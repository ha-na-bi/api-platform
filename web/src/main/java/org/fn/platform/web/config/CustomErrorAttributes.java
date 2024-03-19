package org.fn.platform.web.config;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    final String EXCEPTION_MESSAGE_KEY = "message";

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);

        Throwable error = getError(webRequest);
        if (error instanceof MethodArgumentNotValidException ex) {
            Map<String, String> validationErrors = new HashMap<>();
            ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                    validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage())
            );
            errorAttributes.put(EXCEPTION_MESSAGE_KEY, validationErrors);
        } else if (error instanceof Exception ex) {
            errorAttributes.put(EXCEPTION_MESSAGE_KEY, ex.getMessage());
        }

        return errorAttributes;
    }
}
