package org.fn.platform.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.fn.platform.web.entity.ApiInfo;
import org.fn.platform.web.model.api.ApiModel;
import org.fn.platform.web.model.api.ApiPageQuery;
import org.fn.platform.web.model.app.AppModel;
import org.fn.platform.web.model.app.AppPageQuery;

public interface ApiInfoService extends IService<ApiInfo> {
    Page<ApiModel> page(ApiPageQuery pageQuery);
}
