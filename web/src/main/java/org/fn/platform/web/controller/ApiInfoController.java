package org.fn.platform.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fn.platform.web.entity.ApiInfo;
import org.fn.platform.web.model.api.ApiAddModel;
import org.fn.platform.web.model.api.ApiModel;
import org.fn.platform.web.model.api.ApiPageQuery;
import org.fn.platform.web.model.api.ApiUpdateModel;
import org.fn.platform.web.model.core.CResult;
import org.fn.platform.web.service.ApiInfoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "接口信息")
@RequestMapping("/api-info")
@RequiredArgsConstructor
@RestController
public class ApiInfoController {
    final ApiInfoService apiInfoService;

    @PostMapping("add")
    @ApiOperationSupport(order = 11)
    public CResult<ApiModel> add(@Valid @RequestBody ApiAddModel model) {
        return CResult.ok(apiInfoService.Add(model));
    }

    @DeleteMapping("delete/{id}")
    @Parameter(name = "id", description = "应用标识", required = true)
    @ApiOperationSupport(order = 21)
    public CResult<Boolean> delete(@PathVariable Long id) {
        apiInfoService.checkReference(id);

        return CResult.ok(apiInfoService.removeById(id));
    }

    @PutMapping("update")
    @ApiOperationSupport(order = 31)
    public CResult<Boolean> update(@Valid @RequestBody ApiUpdateModel model) {
        if (apiInfoService.getById(model.getId()) == null) {
            return CResult.notFound();
        }

        return CResult.ok(apiInfoService.updateById(model.toApiInfo()));
    }

    @GetMapping("get/{id}")
    @ApiOperationSupport(order = 41)
    @Parameter(name = "id", description = "接口标识", required = true)
    public CResult<ApiModel> get(@PathVariable String id) {
        return CResult.ok(ApiModel.from(apiInfoService.getById(id)));
    }

    @GetMapping("page")
    @Parameters({
            @Parameter(name = "page", description = "页数", required = true),
            @Parameter(name = "size", description = "分页大小", required = true),
            @Parameter(name = "sort", description = "排序"),
            @Parameter(name = "keyword", description = "关键词查询"),
            @Parameter(name = "status", description = "应用的状态")
    })
    @ApiOperationSupport(order = 42)
    public CResult<Page<ApiModel>> page(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "id,asc") String sort,
                                        @RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) Integer status) {
        return CResult.ok(apiInfoService.page(new ApiPageQuery(page, size, sort, keyword, status)));
    }

    @GetMapping("list")
    @ApiOperationSupport(order = 43)
    public CResult<List<ApiModel>> list() {
        List<ApiInfo> appInfoList = apiInfoService.list();
        List<ApiModel> appModelList = appInfoList.stream().map(ApiModel::from).toList();

        return CResult.ok(appModelList);
    }
}
