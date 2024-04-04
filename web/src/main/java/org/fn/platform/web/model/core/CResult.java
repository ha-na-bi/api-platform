package org.fn.platform.web.model.core;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.fn.platform.web.common.BizException;
import org.fn.platform.web.common.ErrorEnum;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
public class CResult<T> {
    int status;
    String message;
    T Data;
    CError error;

    public static <T> CResult<T> ok() {
        return ok(null, null);
    }

    public static <T> CResult<T> ok(T data) {
        return ok(data, null);
    }

    public static <T> CResult<T> ok(T data, String message) {
        CResult<T> result = new CResult<>();
        result.setStatus(HttpStatus.OK.value());
        result.setData(data);
        result.setMessage(message);

        return result;
    }

    public static <T> CResult<T> notFound() {
        CResult<T> result = new CResult<>();
        result.setStatus(HttpStatus.NOT_FOUND.value());

        CError error = new CError(ErrorEnum.NOT_FOUND);
        result.setError(error);

        return result;
    }

    public static <T> CResult<T> badRequest(String message) {
        CResult<T> result = new CResult<>();
        result.setStatus(HttpStatus.BAD_REQUEST.value());
        result.setMessage(message);

        CError error = new CError(ErrorEnum.BAD_REQUEST);
        result.setError(error);

        return result;
    }

    public static <T> CResult<T> badRequest(Map<String, String> fieldErrors) {
        CResult<T> result = new CResult<>();
        result.setStatus(HttpStatus.BAD_REQUEST.value());

        CError error = new CError(ErrorEnum.BAD_REQUEST);
        error.setFieldErrors(fieldErrors);
        result.setError(error);

        return result;
    }

    public static <T> CResult<T> internalServerError(String message) {
        CResult<T> result = new CResult<>();
        result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.setMessage(message);

        CError error = new CError(ErrorEnum.INTERNAL_SERVER_ERROR);
        result.setError(error);

        return result;
    }

    public static <T> CResult<T> exception(BizException bizException) {
        CResult<T> result = new CResult<>();
        result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.setMessage(bizException.getMessage());

        CError error = new CError(bizException.getErrorEnum());
        result.setError(error);

        return result;
    }


    @Getter
    public static class CError {
        public CError(ErrorEnum errorEnum) {
            this.code = errorEnum.getCode();
            this.description = errorEnum.getDescription();
        }

        String code;
        String description;
        @Setter
        Map<String, String> fieldErrors;
    }

}