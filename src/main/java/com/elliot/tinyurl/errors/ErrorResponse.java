package com.elliot.tinyurl.errors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private int status;
    private String code;
    private String message;
    private String detail;

    public ErrorResponse(ErrorEnum code) {
        this.status = code.getStatus();
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public static ErrorResponse of(ErrorEnum code) {
        return new ErrorResponse(code);
    }
}
