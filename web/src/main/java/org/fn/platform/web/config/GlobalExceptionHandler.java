package org.fn.platform.web.config;

import org.fn.platform.web.model.core.CResult;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

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
    @ExceptionHandler(Exception.class)
    public <T> CResult<T> handleHttpMessageNotReadable(Exception ex) {
        return CResult.internalServerError(ex.getMessage());
    }
}
