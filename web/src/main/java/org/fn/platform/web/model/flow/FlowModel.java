package org.fn.platform.web.model.flow;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.fn.platform.web.entity.FlowInfo;
import org.fn.platform.web.model.app.AppModel;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FlowModel {
    Long id;
    String code;
    String name;
    String summary;
    String data;
    Integer status;
    LocalDateTime createdTime;
    LocalDateTime updatedTime;

    public static FlowModel from(FlowInfo entity) {
        FlowModel model = new FlowModel();
        if (entity != null) {
            BeanUtils.copyProperties(entity, model);
        }

        return model;
    }

    public static Page<FlowModel> from(Page<FlowInfo> page) {
        Page<FlowModel> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<FlowModel> modelList = page.getRecords().stream()
                .map(FlowModel::from).toList();
        pageResult.setRecords(modelList);

        return pageResult;
    }
}
