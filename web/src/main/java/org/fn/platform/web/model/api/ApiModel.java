package org.fn.platform.web.model.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.fn.platform.web.entity.ApiInfo;
import org.fn.platform.web.entity.AppInfo;
import org.fn.platform.web.model.app.AppModel;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
public class ApiModel {
    Long id;
    Long appId;
    String code;
    String name;
    String url;
    List<ApiHeader> header;
    String method;
    String summary;
    List<ApiParameter> parameter;
    Integer status;
    Integer timeout;
    LocalDateTime createdTime;
    LocalDateTime updatedTime;

    public static ApiModel from(ApiInfo apiInfo) {
        ApiModel model = new ApiModel();
        if(Objects.nonNull(apiInfo)){
            BeanUtils.copyProperties(apiInfo, model);
            model.setHeader(ApiHeader.from(apiInfo.getHeader()));
            model.setParameter(ApiParameter.from(apiInfo.getParameter()));
        }

        return model;
    }

    public static Page<ApiModel> from(Page<ApiInfo> page) {
        Page<ApiModel> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<ApiModel> apiModelList = page.getRecords().stream()
                .map(ApiModel::from).toList();
        pageResult.setRecords(apiModelList);

        return pageResult;
    }
}
