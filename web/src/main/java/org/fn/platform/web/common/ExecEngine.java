package org.fn.platform.web.common;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.fn.platform.web.model.engine.ApiInstance;
import org.fn.platform.web.model.flow.FlowData;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ExecEngine {
    static final Pattern EXPRESSION_PATTERN = Pattern.compile("\\{\\{\\s*(.+?)\\s*:\\s*(.+?)\\s*\\}\\}");

    final FlowData flowData;

    Map<String, String> response;

    public ExecEngine(FlowData flowData) {
        this.flowData = flowData;
    }

    public void execute() {
        //TODO: 解析 flowData 并执行整个流程
    }

    @SneakyThrows
    void execute(ApiInstance api) {
        HttpRequest request = createRequest(api);
        long startTime = System.currentTimeMillis();

        HttpResponse httpResponse = null;
        try {
            httpResponse = request.execute();
            response.put(api.getCode(), httpResponse.body());
        } catch (Exception e) {
            throw new ApiCallException(api.getUrl(), e.getMessage(), e);
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }

            record(request, httpResponse, System.currentTimeMillis() - startTime);
        }
    }

    void record(HttpRequest request, HttpResponse response, long duration) {

    }

    HttpRequest createRequest(ApiInstance api) {
        // 实际参数替换
        String body = replaceExpressions(api.getBody());
        Map<String, String> path = api.getPath();
        if (path != null && !path.isEmpty()) {
            path.forEach((key, value) -> path.put(key, replaceExpressions(value)));
        }
        Map<String, String> query = api.getQuery();
        if (query != null && !query.isEmpty()) {
            query.forEach((key, value) -> query.put(key, replaceExpressions(value)));
        }
        Map<String, Object> form = api.getForm();
        if (form != null && !form.isEmpty()) {
            form.forEach((key, value) -> form.put(key, replaceExpressions(value.toString())));
        }

        // 构建请求
        String url = StrTempUtil.format(api.getUrl(), path);
        if (query != null && !query.isEmpty()) {
            url = url + "?" + URLUtil.buildQuery(query, CharsetUtil.CHARSET_UTF_8);
        }
        HttpRequest request = HttpUtil.createRequest(api.getMethod(), url);
        // 设置请求头
        api.getHeader().forEach(map -> request.headerMap(map, true));
        // 设置请求体
        request.body(body);
        request.form(form);

        // 设置超时时间
        request.timeout(api.getTimeout());

        return request;
    }

    String replaceExpressions(String input) {
        Matcher matcher = EXPRESSION_PATTERN.matcher(input);
        StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            String code = matcher.group(1);
            String exp = matcher.group(2);
            String replacement = getDependData(code, exp).toString();
            replacement = Matcher.quoteReplacement(replacement);
            matcher.appendReplacement(result, replacement);
        }

        matcher.appendTail(result);

        return result.toString();
    }

    Object getDependData(String code, String expression) {
        Object responseData = response.get(code);
        if (StringUtils.isEmpty(expression)) {
            return responseData;
        }

        // 从响应中获取数据
        Object result = null;
        String jsonPathExpr = expression.substring(code.length() + 1);
        if (JSONUtil.isTypeJSON((String) responseData)) {
            responseData = JSONUtil.parse(responseData);
            result = JSONUtil.getByPath((JSONObject) responseData, jsonPathExpr);
        } else if (JSONUtil.isTypeJSONArray((String) responseData)) {
            responseData = JSONUtil.parseArray(responseData);
            result = JSONUtil.getByPath((JSONArray) responseData, jsonPathExpr);
        } else {
            log.warn("无法解析的响应数据: {} -> {}", expression, responseData);
        }

        return result;
    }
}
