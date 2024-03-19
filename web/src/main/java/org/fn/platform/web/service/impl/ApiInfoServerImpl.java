package org.fn.platform.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.fn.platform.web.entity.ApiInfo;
import org.fn.platform.web.entity.AppInfo;
import org.fn.platform.web.mapper.ApiInfoMapper;
import org.fn.platform.web.model.api.ApiModel;
import org.fn.platform.web.model.api.ApiPageQuery;
import org.fn.platform.web.model.app.AppModel;
import org.fn.platform.web.service.ApiInfoService;
import org.springframework.stereotype.Service;

@Service
public class ApiInfoServerImpl extends ServiceImpl<ApiInfoMapper, ApiInfo> implements ApiInfoService {
    @Override
    public Page<ApiModel> page(ApiPageQuery pageQuery) {
        LambdaQueryWrapper<ApiInfo> queryWrapper = Wrappers.lambdaQuery(ApiInfo.class);

        pageQuery.apply(queryWrapper);
        Page<ApiInfo> appInfoPageResult = page(pageQuery.toCondition(), queryWrapper);

        return ApiModel.from(appInfoPageResult);
    }
}
