package org.fn.platform.web.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constant {
    public static class Status {
        public final static int DISABLED = 0;
        public final static int ENABLED = 10;
    }

    public static class Default {
        public final static int TIMEOUT = 3000;
    }

    public static class HttpMethod {
        public final static String GET = "GET";
        public final static String POST = "POST";
        public final static String PUT = "PUT";
        public final static String DELETE = "DELETE";
    }

    public final static String CONTENT_TYPE = "application/json";

    public static class ContentType {
        public final static String APPLICATION_JSON = "application/json";
        public final static String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
        public final static String MULTIPART_FORM_DATA = "multipart/form-data";
    }
}
