package org.fn.platform.web.model.app;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.fn.platform.web.common.Constant;
import org.fn.platform.web.entity.AppInfo;
import org.springframework.beans.BeanUtils;

@Data
public class AppAddModel {
    @NotBlank
    String code;
    @NotBlank
    String name;
    @NotBlank
    String url;
    String summary;

    public AppInfo toAppInfo(){
        AppInfo entity = new AppInfo();
        BeanUtils.copyProperties(this, entity);
        entity.setStatus(Constant.Status.ENABLED);

        return entity;
    }
}
