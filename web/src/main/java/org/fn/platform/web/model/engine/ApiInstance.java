package org.fn.platform.web.model.engine;

import cn.hutool.http.Method;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ApiInstance {
    String code;
    Method method;
    String url;
    List<Map<String, String>> header = new ArrayList<>();
    Map<String, String> query = new HashMap<>();
    Map<String, String> path = new HashMap<>();
    String body;
    Map<String, Object> form = new HashMap<>();
    int timeout;
    List<String> dependList = new ArrayList<>();
}
