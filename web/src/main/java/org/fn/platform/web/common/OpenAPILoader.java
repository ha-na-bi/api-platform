package org.fn.platform.web.common;

import cn.hutool.core.collection.CollectionUtil;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.PathParameter;
import io.swagger.v3.oas.models.parameters.QueryParameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.parser.OpenAPIV3Parser;
import lombok.NoArgsConstructor;
import org.fn.platform.web.model.api.ApiAddModel;
import org.fn.platform.web.model.api.ApiHeader;
import org.fn.platform.web.model.api.ApiParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.fn.platform.web.common.Constant.CONTENT_TYPE;


@NoArgsConstructor
public class OpenAPILoader {
    public static void main(String[] args) {
        OpenAPILoader loader = OpenAPILoader.load("http://localhost:8080/v2/api-docs");
        List<ApiAddModel> list = loader.toApiAddModelList();
        list.forEach(System.out::println);
    }

    private OpenAPI openAPI;

    public static OpenAPILoader load(String url) {
        OpenAPILoader loader = new OpenAPILoader();
        loader.openAPI = new OpenAPIV3Parser().read(url);

        return loader;
    }

    public Schema<?> getSchema(String ref) {
        if (openAPI != null && ref != null && ref.startsWith("#/components/schemas/")) {
            String schemaName = ref.substring("#/components/schemas/".length());

            return (Schema<?>) openAPI.getComponents().getSchemas().get(schemaName);
        }

        return null;
    }

    public List<ApiAddModel> toApiAddModelList() {
        List<ApiAddModel> list = new ArrayList<>();

        openAPI.getPaths().forEach((path, pathItem) -> {
            Map<PathItem.HttpMethod, Operation> operationMap = pathItem.readOperationsMap();
            operationMap.forEach((httpMethod, operation) -> {
                list.add(getApiAddModel(path, httpMethod, operation));
            });
        });

        return list;
    }

    private ApiAddModel getApiAddModel(String path, PathItem.HttpMethod httpMethod, Operation operation) {
        ApiAddModel model = new ApiAddModel();
        model.setName(operation.getOperationId());
        model.setUrl(path);
        model.setMethod(httpMethod.toString());
        model.setSummary(operation.getSummary());

        setParameter(model, operation);
        setRequestBody(model, operation);

        return model;
    }

    private void setRequestBody(ApiAddModel model, Operation operation) {
        RequestBody requestBody = operation.getRequestBody();
        if (requestBody == null) {
            return;
        }

        Content content = requestBody.getContent();
        if (content == null) {
            return;
        }

        processJson(model, requestBody, content);
        processForm(model, requestBody, content);
    }

    private void processJson(ApiAddModel model, RequestBody requestBody, Content content) {
        MediaType mediaType = content.get(Constant.ContentType.APPLICATION_JSON);
        if (mediaType == null) {
            return;
        }

        Schema<?> schema = mediaType.getSchema();
        if (schema != null && schema.get$ref() != null) {
            // 设置请求头
            ApiHeader header = new ApiHeader();
            header.setName(CONTENT_TYPE);
            header.setRequired(true);
            header.setDefaultValue(Constant.ContentType.APPLICATION_JSON);
            model.setHeader(List.of(header));
            // 设置参数
            Schema<?> resolvedSchema = getSchema(schema.get$ref());
            ApiParameter apiParameter = new ApiParameter();
            apiParameter.setName(requestBody.getDescription());
            apiParameter.setType(resolvedSchema.getType());
            apiParameter.setIn("body");
            apiParameter.setRequired(requestBody.getRequired());
            apiParameter.setDescription(requestBody.getDescription());

            model.setParameter(List.of(apiParameter));
        }
    }

    private void processForm(ApiAddModel model, RequestBody requestBody, Content content) {
        // 尝试获取两种表单头
        var contentType = Constant.ContentType.APPLICATION_X_WWW_FORM_URLENCODED;
        var mediaType = content.get(contentType);
        if (mediaType == null) {
            contentType = Constant.ContentType.MULTIPART_FORM_DATA;
            mediaType = content.get(contentType);
            if (mediaType == null) {
                return;
            }
        }

        var schema = mediaType.getSchema();
        if (schema == null) {
            return;
        }

        Map<String, Schema<?>> properties = schema.getProperties();
        if (properties == null) {
            return;
        }

        // 设置请求头
        ApiHeader header = new ApiHeader();
        header.setName(CONTENT_TYPE);
        header.setRequired(true);
        header.setDefaultValue(contentType);
        model.setHeader(List.of(header));

        // 设置参数
        List<ApiParameter> parameters = new ArrayList<>();
        for (Map.Entry<String, Schema<?>> entry : properties.entrySet()) {
            String propName = entry.getKey();
            Schema<?> propSchema = entry.getValue();
            ApiParameter apiParameter = new ApiParameter();
            apiParameter.setName(propName);
            apiParameter.setType(propSchema.getType());
            apiParameter.setIn("body");
            apiParameter.setRequired(requestBody.getRequired());
            apiParameter.setDescription(propSchema.getDescription());
            parameters.add(apiParameter);
        }

        model.setParameter(parameters);
    }

    private void setParameter(ApiAddModel model, Operation operation) {
        List<Parameter> parameterList = operation.getParameters();
        if (CollectionUtil.isEmpty(parameterList)) {
            return;
        }

        List<ApiParameter> list = new ArrayList<>();
        for (Parameter parameter : parameterList) {
            ApiParameter apiParameter = new ApiParameter();
            if (parameter instanceof QueryParameter) {
                // 处理查询参数
                setParameter(parameter, apiParameter);
                apiParameter.setIn("query");
                list.add(apiParameter);
            } else if (parameter instanceof PathParameter) {
                // 处理路径参数
                setParameter(parameter, apiParameter);
                apiParameter.setIn("path");
                list.add(apiParameter);
            }
        }

        model.setParameter(list);
    }

    private void setParameter(Parameter parameter, ApiParameter apiParameter) {
        apiParameter.setName(parameter.getName());
        apiParameter.setType(parameter.getSchema().getType());
        apiParameter.setRequired(parameter.getRequired());
        apiParameter.setDefaultValue(parameter.getSchema().getDefault().toString());
        apiParameter.setDescription(parameter.getDescription());
    }
}
