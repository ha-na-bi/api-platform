package org.fn.platform.web.model.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.fn.platform.web.model.core.PageQuery;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class AppPageQuery extends PageQuery {
    String keyword;
    Integer status;
}
