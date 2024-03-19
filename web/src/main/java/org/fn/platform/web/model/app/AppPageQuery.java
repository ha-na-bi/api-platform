package org.fn.platform.web.model.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.fn.platform.web.entity.AppInfo;
import org.fn.platform.web.model.core.PageQuery;

import java.util.Objects;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AppPageQuery extends PageQuery {

    String keyword;
    Integer status;

    public void apply(LambdaQueryWrapper<AppInfo> queryWrapper) {
        if (Objects.nonNull(status)) {
            queryWrapper.eq(AppInfo::getStatus, status);
        }

        if (Objects.nonNull(keyword)) {
            // 关键词查询适用于编码、名称和描述的模糊检索
            queryWrapper.and(wrapper -> wrapper.like(AppInfo::getCode, keyword)
                    .or().like(AppInfo::getName, keyword)
                    .or().like(AppInfo::getSummary, keyword))
                    .or().like(AppInfo::getUrl, keyword);
        }
    }
}
