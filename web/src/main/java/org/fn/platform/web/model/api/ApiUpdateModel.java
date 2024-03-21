package org.fn.platform.web.model.api;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.fn.platform.web.common.Constant;
import org.fn.platform.web.entity.ApiInfo;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
public class ApiUpdateModel {
    @NotNull
    Long id;
    @NotNull
    Long appId;
    @NotBlank
    String name;
    @NotBlank
    String url;
    List<ApiHeader> header;
    @NotBlank
    String method;
    String summary;
    List<ApiParameter> parameter;
    @NotNull
    Integer status;
    Integer timeout;

    public ApiInfo toApiInfo() {
        ApiInfo apiInfo = new ApiInfo();
        BeanUtils.copyProperties(this, apiInfo);

        if (CollectionUtil.isNotEmpty(this.header)) {
            apiInfo.setHeader(JSONUtil.toJsonStr(this.header));
        }
        if (CollectionUtil.isNotEmpty(this.parameter)) {
            apiInfo.setParameter(JSONUtil.toJsonStr(this.parameter));
        }

        return apiInfo;
    }
}
