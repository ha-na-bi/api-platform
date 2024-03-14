package org.fn.platform.web.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * API 接口信息表
 */
@Data
@TableName("api_info")
public class ApiInfo {
    @TableId(value = "id", type = IdType.AUTO)
    Long id;

    Long appId;

    String code;

    String name;

    String url;

    String header;

    String method;

    String summary;

    String parameter;

    Integer status;

    Integer timeout;

    @TableField(fill = FieldFill.INSERT)
    LocalDateTime createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    LocalDateTime updatedTime;
}
