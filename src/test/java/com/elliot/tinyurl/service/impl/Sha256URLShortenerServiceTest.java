package com.elliot.tinyurl.service.impl;

import com.elliot.tinyurl.dto.request.URLRequestDTO;
import com.elliot.tinyurl.service.URLShortenerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class Sha256URLShortenerServiceTest {

    private final URLShortenerService shortenerService;

    public Sha256URLShortenerServiceTest() throws NoSuchAlgorithmException {
        String baseUrl = "https://tiny-url.test.com/";
        MessageDigest md = MessageDigest.getInstance("SHA3-256");
        shortenerService = new Sha256URLShortenerService(baseUrl, md);
    }

    @Test
    @DisplayName("URL 단축 - SHA256 방식")
    public void testEncoding() throws Exception {
        URLRequestDTO url1 = URLRequestDTO.builder().url("https://testUrl1").build();
        URLRequestDTO url2 = URLRequestDTO.builder().url("https://testUrl2").build();

        URLRequestDTO shortUrl1a = shortenerService.shortenUrl(url1);
        URLRequestDTO shortUrl1b = shortenerService.shortenUrl(url1);
        assertEquals(shortUrl1a.getShortUrl(), shortUrl1b.getShortUrl());

        URLRequestDTO shortUrl2 = shortenerService.shortenUrl(url2);
        assertNotEquals(shortUrl1a.getShortUrl(), shortUrl2.getShortUrl());
    }

}