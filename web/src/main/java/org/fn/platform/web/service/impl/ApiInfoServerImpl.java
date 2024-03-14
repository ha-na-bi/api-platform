package org.fn.platform.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.fn.platform.web.entity.ApiInfo;
import org.fn.platform.web.mapper.ApiInfoMapper;
import org.fn.platform.web.service.ApiInfoService;
import org.springframework.stereotype.Service;

@Service
public class ApiInfoServerImpl extends ServiceImpl<ApiInfoMapper, ApiInfo> implements ApiInfoService {
}
