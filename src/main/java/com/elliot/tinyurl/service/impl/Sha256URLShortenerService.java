package com.elliot.tinyurl.service.impl;

import com.elliot.tinyurl.dto.request.URLRequestDTO;
import com.elliot.tinyurl.service.URLShortenerService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Service
@RequiredArgsConstructor
public class Sha256URLShortenerService implements URLShortenerService {

    private final String baseUrl;

    private final MessageDigest md;

    private String hashUrl(String url) {
        byte[] hashBytes = md.digest(url.getBytes(StandardCharsets.UTF_8));
        return baseUrl + HexUtils.toHexString(hashBytes).substring(0, 10);
    }

    @Override
    public URLRequestDTO shortenUrl(URLRequestDTO urlRequestDTO) {
        String longUrl = urlRequestDTO.getUrl();
        String shortUrl = hashUrl(longUrl);
        return URLRequestDTO.builder().url(longUrl).shortUrl(shortUrl).build();
    }

}
