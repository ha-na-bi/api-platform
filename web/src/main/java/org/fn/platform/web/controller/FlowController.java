package org.fn.platform.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fn.platform.web.entity.FlowInfo;
import org.fn.platform.web.model.core.CResult;
import org.fn.platform.web.model.flow.FlowAddModel;
import org.fn.platform.web.model.flow.FlowModel;
import org.fn.platform.web.model.flow.FlowPageQuery;
import org.fn.platform.web.model.flow.FlowUpdateModel;
import org.fn.platform.web.service.FlowInfoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "流程管理")
@RestController
@RequestMapping("/flow")
@RequiredArgsConstructor
public class FlowController {
    final FlowInfoService flowInfoService;

    @PostMapping("add")
    @ApiOperationSupport(order = 11)
    public CResult<FlowModel> add(@Valid @RequestBody FlowAddModel model) {
        return CResult.ok(flowInfoService.add(model));
    }

    @DeleteMapping("delete/{id}")
    @Parameter(name = "id", description = "应用标识", required = true)
    @ApiOperationSupport(order = 21)
    public CResult<Boolean> delete(@PathVariable Long id) {
        return CResult.ok(flowInfoService.delete(id));
    }

    @PutMapping("update")
    @ApiOperationSupport(order = 31)
    public CResult<Boolean> update(@Valid @RequestBody FlowUpdateModel model) {
        return CResult.ok(flowInfoService.update(model));
    }

    @GetMapping("get/{id}")
    @ApiOperationSupport(order = 41)
    @Parameter(name = "id", description = "应用标识", required = true)
    public CResult<FlowModel> get(@PathVariable Long id) {
        FlowInfo entity = flowInfoService.getById(id);
        if (entity == null) {
            return CResult.notFound();
        }

        return CResult.ok(FlowModel.from(entity));
    }

    @GetMapping("page")
    @Parameters({
            @Parameter(name = "page", description = "页数", required = true),
            @Parameter(name = "size", description = "分页大小", required = true),
            @Parameter(name = "sort", description = "排序"),
            @Parameter(name = "keyword", description = "关键词查询"),
            @Parameter(name = "status", description = "状态")
    })
    @ApiOperationSupport(order = 42)
    public CResult<Page<FlowModel>> page(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "id,asc") String sort,
                                         @RequestParam(required = false) String keyword,
                                         @RequestParam(required = false) Integer status) {
        return CResult.ok(flowInfoService.page(new FlowPageQuery(page, size, sort, keyword, status)));
    }

    @GetMapping("list")
    @ApiOperationSupport(order = 43)
    public CResult<List<FlowModel>> list() {
        List<FlowInfo> entityList = flowInfoService.list();
        List<FlowModel> modelList = entityList.stream().map(FlowModel::from).toList();

        return CResult.ok(modelList);
    }
}
