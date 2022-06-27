package com.elliot.tinyurl.dto.response;

import lombok.Getter;

@Getter
public class URLResponseDTO {

    private String url;

    public URLResponseDTO(String url) {
        this.url = url;
    }

    public static URLResponseDTO of(String url) {
        return new URLResponseDTO(url);
    }
}
