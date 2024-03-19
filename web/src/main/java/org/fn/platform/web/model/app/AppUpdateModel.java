package org.fn.platform.web.model.app;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.fn.platform.web.entity.AppInfo;

@Data
public class AppUpdateModel {
    @NotNull
    Long id;
    @NotBlank
    String name;
    @NotBlank
    String url;
    String summary;

    public AppInfo toAppInfo() {
        AppInfo entity = new AppInfo();
        entity.setId(id);
        entity.setName(name);
        entity.setUrl(url);
        entity.setSummary(summary);

        return entity;
    }
}
