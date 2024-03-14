package org.fn.platform.web.service.impl;

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
        throw new UnsupportedOperationException();
    }
}
