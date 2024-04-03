package org.fn.platform.web.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("execute_log")
public class ExecuteLog {
    Long id;
    Long flowId;
    Long appId;
    Long apiId;
    String request;
    String response;
    Integer status;
    Long spendTime;
    String content;
    Long createdTime;
}
