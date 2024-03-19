package org.fn.platform.web.model.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fn.platform.web.entity.ApiInfo;
import org.fn.platform.web.model.core.PageQuery;

import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApiPageQuery extends PageQuery {
    public ApiPageQuery(int page, int size, String sort, String keyword, Integer status) {
        super(page, size, sort);
        this.keyword = keyword;
        this.status = status;
    }

    String keyword;
    Integer status;

    public void apply(LambdaQueryWrapper<ApiInfo> queryWrapper) {
        if (Objects.nonNull(status)) {
            queryWrapper.eq(ApiInfo::getStatus, status);
        }

        if (Objects.nonNull(keyword)) {
            // 关键词查询适用于编码、名称和描述的模糊检索
            queryWrapper.and(wrapper -> wrapper.like(ApiInfo::getCode, keyword)
                            .or().like(ApiInfo::getName, keyword)
                            .or().like(ApiInfo::getSummary, keyword))
                    .or().like(ApiInfo::getUrl, keyword);
        }
    }
}
