package org.fn.platform.web.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.fn.platform.web.entity.FlowApiRelation;
import org.fn.platform.web.mapper.FlowApiRelationMapper;
import org.springframework.stereotype.Service;

@Service
public class FlowApiRelationServerImpl extends ServiceImpl<FlowApiRelationMapper, FlowApiRelation> implements FlowApiRelationService {
}
