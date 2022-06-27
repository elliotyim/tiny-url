package com.elliot.tinyurl.util.impl;

import com.elliot.tinyurl.util.URLShortener;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class Sha256URLShortenerTest {

    private final URLShortener urlShortener;

    public Sha256URLShortenerTest() throws NoSuchAlgorithmException {
        String baseUrl = "https://tiny-url.test.com/";
        MessageDigest md = MessageDigest.getInstance("SHA3-256");
        urlShortener = new Sha256URLShortener(baseUrl, md);
    }

    @Test
    @DisplayName("URL 단축 - SHA256 방식")
    public void testEncoding() throws Exception {
        String url1 = "https://testUrl1";
        String url2 = "https://testUrl2";

        String shortUrl1a = urlShortener.shortenUrl(url1);
        String shortUrl1b = urlShortener.shortenUrl(url1);
        assertEquals(shortUrl1a, shortUrl1b);

        String shortUrl2 = urlShortener.shortenUrl(url2);
        assertNotEquals(shortUrl1a, shortUrl2);
    }

}