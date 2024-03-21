package org.fn.platform.web.model.core;

import lombok.Getter;

@Getter
public enum ErrorEnum {
    VALIDATION_FAILED("VALIDATION_FAILED", "输入的数据验证未通过。"),
    NOT_FOUND("NOT_FOUND", "服务器无法根据客户端的请求找到资源。"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "服务器遇到了一个未曾预料到的状况，无法完成对请求的处理。"),
    UNKNOWN_ERROR("UNKNOWN_ERROR", "发生未知错误。"),
    NOT_IMPLEMENTED("NOT_IMPLEMENTED", "服务器不支持当前请求所需要的某个功能。"),
    METHOD_NOT_ALLOWED("METHOD_NOT_ALLOWED", "客户端请求中的方法被禁止。"),
    BIZ_EXCEPTION("BIZ_EXCEPTION","服务器发现了一个业务异常，请按照提示信息进行处理。"),
    BAD_REQUEST("BAD_REQUEST", "请求无效或无法被服务器理解。");

    private final String code;
    private final String description;

    ErrorEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
