package org.fn.platform.web.config;

import lombok.extern.slf4j.Slf4j;
import org.fn.platform.web.model.core.BizException;
import org.fn.platform.web.model.core.CResult;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public <T> CResult<T> handleHttpMessageNotReadable(MethodArgumentNotValidException ex) {
        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage())
        );

        return CResult.badRequest(validationErrors);
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public <T> CResult<T> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return CResult.badRequest(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(BizException.class)
    public <T> CResult<T> handleBizException(BizException ex) {
        log.warn("业务异常", ex);
        return CResult.exception(ex);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public <T> CResult<T> handleHttpMessageNotReadable(Exception ex) {
        log.error("未知异常", ex);
        return CResult.internalServerError(ex.getMessage());
    }
}
