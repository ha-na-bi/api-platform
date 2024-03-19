package org.fn.platform.web.model.app;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.fn.platform.web.entity.AppInfo;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;

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

    public static AppModel from(AppInfo appInfo) {
        AppModel appModel = new AppModel();
        if (appInfo != null) {
            BeanUtils.copyProperties(appInfo, appModel);
        }

        return appModel;
    }

    public static Page<AppModel> from(Page<AppInfo> page) {
        Page<AppModel> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<AppModel> appModelList = page.getRecords().stream()
                .map(AppModel::from).toList();
        pageResult.setRecords(appModelList);

        return pageResult;
    }
}
