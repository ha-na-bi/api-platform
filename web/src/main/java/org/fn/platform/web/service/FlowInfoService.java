package org.fn.platform.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.fn.platform.web.entity.FlowInfo;
import org.fn.platform.web.model.flow.FlowAddModel;
import org.fn.platform.web.model.flow.FlowModel;
import org.fn.platform.web.model.flow.FlowPageQuery;
import org.fn.platform.web.model.flow.FlowUpdateModel;

public interface FlowInfoService extends IService<FlowInfo> {
    Page<FlowModel> page(FlowPageQuery pageQuery);
    FlowModel add(FlowAddModel model);
    boolean delete(Long id);
    boolean update(FlowUpdateModel model);
}
