package org.fn.platform.web.common;

import java.util.Map;

public class StrTempUtil {
    public static String format(String template, Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return template;
        }
        for (Map.Entry<String, String> entry : params.entrySet()) {
            template = template.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        
        return template;
    }
}
