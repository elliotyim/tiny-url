package com.elliot.tinyurl.errors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode implements ErrorEnum {
    INVALID_PARAMETER(400, "0001", "Invalid Parameter");

    private int status;
    private String code;
    private String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
