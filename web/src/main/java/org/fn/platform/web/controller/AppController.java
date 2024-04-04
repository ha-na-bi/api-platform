package org.fn.platform.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fn.platform.web.entity.AppInfo;
import org.fn.platform.web.model.app.AppAddModel;
import org.fn.platform.web.model.app.AppModel;
import org.fn.platform.web.model.app.AppPageQuery;
import org.fn.platform.web.model.app.AppUpdateModel;
import org.fn.platform.web.model.core.CResult;
import org.fn.platform.web.service.AppInfoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "插件管理")
@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
public class AppController {
    final AppInfoService appInfoService;

    @PostMapping("add")
    @ApiOperationSupport(order = 11)
    public CResult<AppModel> add(@Valid @RequestBody AppAddModel model) {
        AppInfo appInfo = model.toEntity();
        appInfoService.save(appInfo);

        return CResult.ok(AppModel.from(appInfo));
    }

    @DeleteMapping("delete/{id}")
    @Parameter(name = "id", description = "应用标识", required = true)
    @ApiOperationSupport(order = 21)
    public CResult<Boolean> delete(@PathVariable Long id) {
        if (appInfoService.getById(id) == null) {
            return CResult.notFound();
        }

        return CResult.ok(appInfoService.removeById(id));
    }

    @PutMapping("update")
    @ApiOperationSupport(order = 31)
    public CResult<Boolean> update(@Valid @RequestBody AppUpdateModel model) {
        if (appInfoService.getById(model.getId()) == null) {
            return CResult.notFound();
        }

        return CResult.ok(appInfoService.updateById(model.toEntity()));
    }

    @GetMapping("get/{id}")
    @ApiOperationSupport(order = 41)
    @Parameter(name = "id", description = "应用标识", required = true)
    public CResult<AppModel> get(@PathVariable Long id) {
        AppInfo appInfo = appInfoService.getById(id);
        if (appInfo == null) {
            return CResult.notFound();
        }

        return CResult.ok(AppModel.from(appInfo));
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
    public CResult<Page<AppModel>> page(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "id,asc") String sort,
                                        @RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) Integer status) {
        return CResult.ok(appInfoService.page(new AppPageQuery(page, size, sort, keyword, status)));
    }

    @GetMapping("list")
    @ApiOperationSupport(order = 43)
    public CResult<List<AppModel>> list() {
        List<AppInfo> appInfoList = appInfoService.list();
        List<AppModel> appModelList = appInfoList.stream().map(AppModel::from).toList();

        return CResult.ok(appModelList);
    }
}
