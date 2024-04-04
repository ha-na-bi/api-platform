package org.fn.platform.web.common;

import cn.hutool.core.util.StrUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.fn.platform.web.common.ErrorEnum;

@Getter
@EqualsAndHashCode(callSuper = true)
public class BizException extends Exception {
    private final ErrorEnum errorEnum;
    private String message;

    public BizException(String message, Object... args) {
        super(message);
        this.message = StrUtil.format(message, args);
        this.errorEnum = ErrorEnum.BIZ_EXCEPTION;
    }

    public BizException(ErrorEnum errorEnum) {
        super();
        this.errorEnum = errorEnum;
    }

    public BizException(ErrorEnum errorEnum, String message, Object... args) {
        super(message);
        this.message = StrUtil.format(message, args);
        this.errorEnum = errorEnum;
    }
}
