package org.fn.platform.web.model.flow;

import lombok.Data;
import org.fn.platform.web.model.engine.ApiInstance;

import java.util.*;

@Data
public class FlowData {

    List<ApiInstance> apiInstanceList = new ArrayList<>();

    public List<ApiInstance> topologicalSort() {
        // 用于存储拓扑排序的结果
        List<ApiInstance> sortedList = new ArrayList<>();
        // 初始化入度表
        Map<String, Integer> inDegree = new HashMap<>();
        // 初始化图
        Map<String, List<String>> graph = new HashMap<>();
        for (ApiInstance instance : apiInstanceList) {
            inDegree.put(instance.getCode(), 0);
            graph.put(instance.getCode(), new ArrayList<>());
        }
        // 构建图和入度表
        for (ApiInstance instance : apiInstanceList) {
            for (String dep : instance.getDependList()) {
                graph.get(dep).add(instance.getCode());
                inDegree.put(instance.getCode(), inDegree.get(instance.getCode()) + 1);
            }
        }
        // 找到所有入度为0的节点
        Queue<String> queue = new LinkedList<>();
        for (String code : inDegree.keySet()) {
            if (inDegree.get(code) == 0) {
                queue.add(code);
            }
        }
        // 执行拓扑排序
        while (!queue.isEmpty()) {
            String code = queue.poll();
            for (ApiInstance instance : apiInstanceList) {
                if (instance.getCode().equals(code)) {
                    sortedList.add(instance);
                    break;
                }
            }
            for (String neighbor : graph.get(code)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }
        // 检查是否存在环
        if (sortedList.size() != apiInstanceList.size()) {
            throw new RuntimeException("存在循环依赖，无法进行拓扑排序");
        }

        apiInstanceList = sortedList;
        return sortedList;
    }

    public String toJson() {
        return null;
    }
}
