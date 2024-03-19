package org.fn.platform.web.model.core;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.fn.platform.web.entity.AppInfo;

@Data
@Slf4j
public class PageQuery {
    static final String SORT_RULE_DELIMITER = ";";
    static final String SORT_CONDITION_DELIMITER = ",";
    static final String SORT_DIRECTION_ASC = "asc";
    static final String SORT_DIRECTION_DESC = "desc";

    public PageQuery() {
    }

    public PageQuery(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public PageQuery(int page, int size, String sort) {
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    int page;
    int size;
    String sort;

    public <T> Page<T> toCondition() {
        Page<T> page = Page.of(this.page, this.size);

        // col,dire;col,dire;
        if (StringUtils.isNotBlank(this.sort)) {
            for (String rule : sort.split(SORT_RULE_DELIMITER)) {
                if (StringUtils.isNotBlank(rule)) {
                    String[] condition = rule.split(SORT_CONDITION_DELIMITER);
                    if (condition.length >= 2) {
                        String columnName = condition[0];
                        String orderDirection = condition[1].trim().toLowerCase();

                        switch (orderDirection) {
                            case SORT_DIRECTION_ASC:
                                page.addOrder(OrderItem.asc(columnName));
                                break;
                            case SORT_DIRECTION_DESC:
                                page.addOrder(OrderItem.desc(columnName));
                                break;
                            default:
                                log.warn("排序条件不合法，忽略 {} 规则", rule);
                                break;
                        }
                    }
                }
            }
        }

        return page;
    }
}
