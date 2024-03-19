package org.fn.platform.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fn.platform.web.entity.AppInfo;
import org.fn.platform.web.model.app.AppAddModel;
import org.fn.platform.web.model.app.AppModel;
import org.fn.platform.web.model.app.AppPageQuery;
import org.fn.platform.web.service.AppInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "应用信息")
@RestController
@RequestMapping("/app-info")
@RequiredArgsConstructor
public class AppInfoController {
    final AppInfoService appInfoService;

    @Operation(summary = "获取所有应用信息列表")
    @GetMapping("list")
    public ResponseEntity<List<AppModel>> list() {
        List<AppInfo> appInfoList = appInfoService.list();
        List<AppModel> appModelList = appInfoList.stream().map(AppModel::from).toList();

        return ResponseEntity.ok(appModelList);
    }

    @Operation(summary = "获取应用信息")
    @Parameter(name = "id", description = "应用标识", required = true)
    @GetMapping("get/{id}")
    public ResponseEntity<AppModel> get(@PathVariable Long id) {
        AppInfo appInfo = appInfoService.getById(id);
        if (appInfo == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(AppModel.from(appInfo));
    }

    @Operation(summary = "分页获取应用信息")
    @GetMapping("page")
    @Parameters({
            @Parameter(name = "page", description = "页数", required = true),
            @Parameter(name = "size", description = "分页大小", required = true),
            @Parameter(name = "sort", description = "排序"),
            @Parameter(name = "keyword", description = "关键词查询"),
            @Parameter(name = "status", description = "应用的状态")
    })
    public ResponseEntity<Page<AppModel>> page(@RequestParam(defaultValue = "1") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestParam(defaultValue = "id,asc") String sort,
                                               @RequestParam(required = false) String keyword,
                                               @RequestParam(required = false) Integer status) {
        AppPageQuery query = new AppPageQuery();
        query.setPage(page);
        query.setKeyword(keyword);
        query.setSize(size);
        query.setSort(sort);
        query.setStatus(status);

        return ResponseEntity.ok(appInfoService.page(query));
    }

    @PostMapping("add")
    @Operation(summary = "新增应用信息")
    public ResponseEntity<AppModel> add(@Valid @RequestBody AppAddModel model) {
        AppInfo appInfo = model.toAppInfo();
        appInfoService.save(appInfo);

        return ResponseEntity.ok(AppModel.from(appInfo));
    }
}
