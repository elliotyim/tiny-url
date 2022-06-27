package com.elliot.tinyurl.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class URLRequestDTO {
    @NotBlank
    @Size(min = 1, max = 1300)
    private String url;

    private String shortUrl;

    @Builder
    public URLRequestDTO(String url, String shortUrl) {
        this.url = url;
        this.shortUrl = shortUrl;
    }
}

