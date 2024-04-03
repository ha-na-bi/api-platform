package org.fn.platform.web.model.api;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApiHeader {
    String name;
    boolean required;
    String defaultValue;

    public static List<ApiHeader> from(String json) {
        if (!JSONUtil.isTypeJSONArray(json)) {
            return new ArrayList<>(0);
        }

        return JSONUtil.toBean(json, new TypeReference<>() {
        },true);
    }
}
