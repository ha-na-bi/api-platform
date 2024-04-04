package org.fn.platform.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.fn.platform.web.entity.FlowInfo;
import org.fn.platform.web.mapper.FlowInfoMapper;
import org.fn.platform.web.common.BizException;
import org.fn.platform.web.model.flow.FlowAddModel;
import org.fn.platform.web.model.flow.FlowModel;
import org.fn.platform.web.model.flow.FlowPageQuery;
import org.fn.platform.web.model.flow.FlowUpdateModel;
import org.fn.platform.web.service.FlowApiRelationService;
import org.fn.platform.web.service.FlowInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FlowInfoServerImpl extends ServiceImpl<FlowInfoMapper, FlowInfo> implements FlowInfoService {
    final FlowApiRelationService flowApiRelationService;

    public Page<FlowModel> page(FlowPageQuery pageQuery) {
        LambdaQueryWrapper<FlowInfo> queryWrapper = Wrappers.lambdaQuery(FlowInfo.class);

        pageQuery.apply(queryWrapper);
        Page<FlowInfo> pageResult = page(pageQuery.toCondition(), queryWrapper);

        return FlowModel.from(pageResult);
    }

    @Override
    @SneakyThrows
    @Transactional
    public FlowModel add(FlowAddModel model) {
        if (exists(Wrappers.<FlowInfo>lambdaQuery().eq(FlowInfo::getCode, model.getCode()))) {
            throw new BizException("客户端提供的 Code [{}] 已存在。", model.getCode());
        }

        FlowInfo entity = model.toEntity();
        save(entity); //保存，获得主键

        // TODO: 需要从 FlowAddModel 中获取 flowApiRelationModel

        return FlowModel.from(entity);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        if (getById(id) == null) {
            return false;
        }

        return removeById(id)
                && flowApiRelationService.deleteByFlowId(id);
    }

    @Override
    @SneakyThrows
    @Transactional
    public boolean update(FlowUpdateModel model) {
        if (getById(model.getId()) == null) {
            throw new BizException("客户端提供的 Id [{}] 不存在。", model.getId());
        }

        updateById(model.toEntity());

        // 先移除关联关系
        flowApiRelationService.deleteByFlowId(model.getId());
        // TODO: 需要从 FlowAddModel 中获取 flowApiRelationModel

        return true;
    }
}
