package org.fn.platform.web.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("flow_api_relation")
public class FlowApiRelation {
    Long id;
    Long flowId;
    Long appId;
    Long ApiId;
}
