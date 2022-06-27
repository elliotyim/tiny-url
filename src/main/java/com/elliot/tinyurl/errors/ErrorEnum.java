package com.elliot.tinyurl.errors;

public interface ErrorEnum {
    int getStatus();
    String getCode();
    String getMessage();
}
