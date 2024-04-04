package org.fn.platform.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.fn.platform.web.entity.AppInfo;
import org.fn.platform.web.entity.FlowInfo;
import org.fn.platform.web.mapper.FlowInfoMapper;
import org.fn.platform.web.model.app.AppModel;
import org.fn.platform.web.model.app.AppPageQuery;
import org.fn.platform.web.model.flow.FlowModel;
import org.fn.platform.web.model.flow.FlowPageQuery;
import org.fn.platform.web.service.FlowInfoService;
import org.springframework.stereotype.Service;

@Service
public class FlowInfoServerImpl extends ServiceImpl<FlowInfoMapper, FlowInfo> implements FlowInfoService {
    public Page<FlowModel> page(FlowPageQuery pageQuery) {
        LambdaQueryWrapper<FlowInfo> queryWrapper = Wrappers.lambdaQuery(FlowInfo.class);

        pageQuery.apply(queryWrapper);
        Page<FlowInfo> pageResult = page(pageQuery.toCondition(), queryWrapper);

        return FlowModel.from(pageResult);
    }
}
