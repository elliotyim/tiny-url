package com.elliot.tinyurl.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class URLRequestDTO {
    private String url;
    private String shortUrl;

    @Builder
    public URLRequestDTO(String url, String shortUrl) {
        this.url = url;
        this.shortUrl = shortUrl;
    }
}

