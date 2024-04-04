package org.fn.platform.web.model.flow;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.fn.platform.web.entity.FlowInfo;
import org.fn.platform.web.model.core.PageQuery;

import java.util.Objects;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FlowPageQuery extends PageQuery {
    public FlowPageQuery(int page, int size, String sort, String keyword, Integer status) {
        super(page, size, sort);
        this.keyword = keyword;
        this.status = status;
    }

    String keyword;
    Integer status;

    public void apply(LambdaQueryWrapper<FlowInfo> queryWrapper) {
        if (Objects.nonNull(status)) {
            queryWrapper.eq(FlowInfo::getStatus, status);
        }

        if (Objects.nonNull(keyword)) {
            // 关键词查询适用于编码、名称和描述的模糊检索
            queryWrapper.and(wrapper -> wrapper.like(FlowInfo::getCode, keyword)
                    .or().like(FlowInfo::getName, keyword)
                    .or().like(FlowInfo::getSummary, keyword));
        }
    }
}
