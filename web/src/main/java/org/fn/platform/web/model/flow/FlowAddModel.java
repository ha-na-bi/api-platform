package org.fn.platform.web.model.flow;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.fn.platform.web.common.Constant;
import org.fn.platform.web.entity.FlowInfo;
import org.springframework.beans.BeanUtils;

@Data
public class FlowAddModel {
    @NotBlank
    String code;
    @NotBlank
    String name;
    String summary;
    @NotBlank
    String data;

    public FlowInfo toEntity() {
        FlowInfo entity = new FlowInfo();
        BeanUtils.copyProperties(this, entity);
        entity.setStatus(Constant.Status.ENABLED);

        return entity;
    }
}
