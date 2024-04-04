package org.fn.platform.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.fn.platform.web.entity.FlowInfo;
import org.fn.platform.web.model.flow.FlowModel;
import org.fn.platform.web.model.flow.FlowPageQuery;

public interface FlowInfoService extends IService<FlowInfo> {
    Page<FlowModel> page(FlowPageQuery pageQuery);
}
