package org.fn.platform.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.fn.platform.web.entity.FlowApiRelation;

public interface FlowApiRelationService extends IService<FlowApiRelation> {
    boolean deleteByFlowId(Long flowId);
}
