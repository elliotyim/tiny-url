package com.elliot.tinyurl.advice;

import com.elliot.tinyurl.errors.ErrorCode;
import com.elliot.tinyurl.errors.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getAllErrors()
                .forEach(e -> errors.append(((FieldError) e).getField() + " : " + e.getDefaultMessage() + "; " ));

        ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_PARAMETER);
        response.setDetail(errors.toString());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleRequestBodyMissingException(HttpMessageNotReadableException ex) {
        ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_PARAMETER);
        response.setDetail(ex.getMessage().split(":")[0]);
        return ResponseEntity.badRequest().body(response);
    }
}
