package org.fn.platform.web.model.app;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.fn.platform.web.entity.AppInfo;
import org.springframework.beans.BeanUtils;

@Data
public class AppUpdateModel {
    @NotNull
    Long id;
    @NotBlank
    String name;
    @NotBlank
    String url;
    String summary;
    @NotNull
    Integer status;

    public AppInfo toEntity() {
        AppInfo entity = new AppInfo();
        BeanUtils.copyProperties(this, entity);

        return entity;
    }
}
