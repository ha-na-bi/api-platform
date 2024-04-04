package org.fn.platform.web.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class ApiCallException extends BizException {
    final String url;
    final String message;

    public ApiCallException(String url, String message) {
        super(message);
        this.url = url;
        this.message = message;
    }

    public ApiCallException(String url, String message, Throwable cause) {
        super(message);
        this.url = url;
        this.message = message;
        initCause(cause);
    }
}
