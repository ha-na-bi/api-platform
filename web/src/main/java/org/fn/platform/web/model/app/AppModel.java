package org.fn.platform.web.model.app;

import lombok.Data;
import org.fn.platform.web.entity.AppInfo;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
public class AppModel {
    Long id;
    String code;
    String name;
    String url;
    Integer status;
    String summary;
    LocalDateTime createdTime;
    LocalDateTime updatedTime;

    public static AppModel form(AppInfo appInfo) {
        AppModel appModel = new AppModel();
        if (appInfo != null) {
            BeanUtils.copyProperties(appInfo, appModel);
        }

        return appModel;
    }
}
