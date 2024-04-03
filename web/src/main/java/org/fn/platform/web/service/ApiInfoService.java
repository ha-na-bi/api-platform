package org.fn.platform.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.fn.platform.web.entity.ApiInfo;
import org.fn.platform.web.model.api.ApiAddModel;
import org.fn.platform.web.model.api.ApiModel;
import org.fn.platform.web.model.api.ApiPageQuery;

public interface ApiInfoService extends IService<ApiInfo> {
    Page<ApiModel> page(ApiPageQuery pageQuery);
    ApiModel Add(ApiAddModel model);
    void checkReference(Long id);
}
