package org.fn.platform.web.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("flow_info")
public class FlowInfo {
    Long id;
    String code;
    String name;
    String summary;
    String data;
    Integer status;
    @TableField(fill = FieldFill.INSERT)
    LocalDateTime createdTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    LocalDateTime updatedTime;
}
