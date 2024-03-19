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

    @PostMapping("add")
    @ApiOperationSupport(order = 11)
    public ResponseEntity<AppModel> add(@Valid @RequestBody AppAddModel model) {
        AppInfo appInfo = model.toAppInfo();
        appInfoService.save(appInfo);

        return ResponseEntity.ok(AppModel.from(appInfo));
    }

    @DeleteMapping("delete/{id}")
    @Parameter(name = "id", description = "应用标识", required = true)
    @ApiOperationSupport(order = 21)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (appInfoService.getById(id) == null) {
            return ResponseEntity.notFound().build();
        }

        appInfoService.removeById(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("update")
    @ApiOperationSupport(order = 31)
    public ResponseEntity<AppModel> update(@Valid @RequestBody AppUpdateModel model) {
        if (appInfoService.getById(model.getId()) == null) {
            return ResponseEntity.notFound().build();
        }
        
        AppInfo appInfo = model.toAppInfo();
        appInfoService.updateById(appInfo);

        return ResponseEntity.ok(AppModel.from(appInfo));
    }

    @GetMapping("get/{id}")
    @ApiOperationSupport(order = 41)
    @Parameter(name = "id", description = "应用标识", required = true)
    public ResponseEntity<AppModel> get(@PathVariable Long id) {
        AppInfo appInfo = appInfoService.getById(id);
        if (appInfo == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(AppModel.from(appInfo));
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

    @GetMapping("list")
    @ApiOperationSupport(order = 43)
    public ResponseEntity<List<AppModel>> list() {
        List<AppInfo> appInfoList = appInfoService.list();
        List<AppModel> appModelList = appInfoList.stream().map(AppModel::from).toList();

        return ResponseEntity.ok(appModelList);
    }
}
