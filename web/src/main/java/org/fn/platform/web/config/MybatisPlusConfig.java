package org.fn.platform.web.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "org.fn.platform.web.mapper")
public class MybatisPlusConfig {

    /**
     * 添加插件
     * 如果配置多个插件,切记分页最后添加
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 分页插件 https://baomidou.com/pages/97710a/
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.SQLITE));

        return interceptor;
    }
}