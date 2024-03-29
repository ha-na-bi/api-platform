package org.fn.platform.web.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 应用信息
 */
@Data
@TableName("app_info")
public class AppInfo {
    @TableId(value = "id", type = IdType.AUTO)
    Long id;

    String code;

    String name;

    String url;

    Integer status;

    String summary;

    @TableField(fill = FieldFill.INSERT)
    LocalDateTime createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    LocalDateTime updatedTime;
}
