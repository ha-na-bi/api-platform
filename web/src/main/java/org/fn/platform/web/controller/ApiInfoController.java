package org.fn.platform.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.fn.platform.web.model.api.ApiModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "ApiInfo", description = "接口信息")
@RequestMapping("/api-info")
@RequiredArgsConstructor
@RestController
public class ApiInfoController {
    @Operation(summary = "获取所有接口信息列表")
    @GetMapping("list")
    public ResponseEntity<List<ApiModel>> list() {
        return ResponseEntity.ok(null);
    }
}
