package org.fn.platform.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.fn.platform.web.entity.AppInfo;
import org.fn.platform.web.mapper.AppInfoMapper;
import org.fn.platform.web.model.app.AppModel;
import org.fn.platform.web.model.app.AppPageQuery;
import org.fn.platform.web.service.AppInfoService;
import org.springframework.stereotype.Service;

@Service
public class AppInfoServerImpl extends ServiceImpl<AppInfoMapper, AppInfo> implements AppInfoService {
    public Page<AppModel> page(AppPageQuery pageQuery) {
        LambdaQueryWrapper<AppInfo> queryWrapper = Wrappers.lambdaQuery(AppInfo.class);

        pageQuery.apply(queryWrapper);
        Page<AppInfo> appInfoPageResult = page(pageQuery.toCondition(), queryWrapper);

        return AppModel.from(appInfoPageResult);
    }
}
