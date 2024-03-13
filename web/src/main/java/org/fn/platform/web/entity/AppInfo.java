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

    /**
     * 应用唯一标识
     */
    String code;

    /**
     * 应用名称
     */
    String name;

    /**
     * 应用的服务地址
     */
    String serviceUrl;

    String summary;

    @TableField(fill = FieldFill.INSERT)
    LocalDateTime createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    LocalDateTime updatedTime;
}
