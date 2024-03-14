package org.fn.platform.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.fn.platform.web.entity.AppInfo;
import org.fn.platform.web.model.app.AppModel;
import org.fn.platform.web.model.app.AppPageQuery;

public interface AppInfoService extends IService<AppInfo> {
    Page<AppModel> page(AppPageQuery pageQuery);
}
