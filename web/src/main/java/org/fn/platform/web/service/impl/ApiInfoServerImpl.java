package org.fn.platform.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.fn.platform.web.entity.ApiInfo;
import org.fn.platform.web.entity.AppInfo;
import org.fn.platform.web.mapper.ApiInfoMapper;
import org.fn.platform.web.mapper.AppInfoMapper;
import org.fn.platform.web.model.api.ApiAddModel;
import org.fn.platform.web.model.api.ApiModel;
import org.fn.platform.web.model.api.ApiPageQuery;
import org.fn.platform.web.common.BizException;
import org.fn.platform.web.service.ApiInfoService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiInfoServerImpl extends ServiceImpl<ApiInfoMapper, ApiInfo> implements ApiInfoService {

    final AppInfoMapper appInfoMapper;

    @Override
    public Page<ApiModel> page(ApiPageQuery pageQuery) {
        LambdaQueryWrapper<ApiInfo> queryWrapper = Wrappers.lambdaQuery(ApiInfo.class);

        pageQuery.apply(queryWrapper);
        Page<ApiInfo> appInfoPageResult = page(pageQuery.toCondition(), queryWrapper);

        return ApiModel.from(appInfoPageResult);
    }

    @Override
    @SneakyThrows
    public ApiModel Add(ApiAddModel model) {
        AppInfo appInfo = appInfoMapper.selectById(model.getAppId());
        if (null == appInfo) {
            throw new BizException("客户端提供的 AppId [{}] 不存在。", model.getAppId());
        }

        if (exists(Wrappers.<ApiInfo>lambdaQuery().eq(ApiInfo::getCode, model.getCode()))) {
            throw new BizException("客户端提供的 Code [{}] 已存在。", model.getCode());
        }

        ApiInfo apiInfo = model.toApiInfo();
        save(apiInfo);

        return ApiModel.from(apiInfo);
    }

    @Override
    @SneakyThrows
    public void checkReference(Long id){
        ApiInfo entity = getById(id);
        if (entity == null) {
            throw new BizException("要删除的接口 {} 不存在。", id);
        }
    }
}
