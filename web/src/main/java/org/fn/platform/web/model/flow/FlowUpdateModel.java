package org.fn.platform.web.model.flow;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.fn.platform.web.entity.FlowInfo;
import org.springframework.beans.BeanUtils;

@Data
public class FlowUpdateModel {
    @NotNull
    Long id;
    @NotBlank
    String name;
    String summary;
    @NotBlank
    String data;
    @NotNull
    Integer status;

    public FlowInfo toEntity() {
        FlowInfo entity = new FlowInfo();
        BeanUtils.copyProperties(this, entity);

        return entity;
    }
}
