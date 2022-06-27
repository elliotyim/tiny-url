package com.elliot.tinyurl.util.impl;

import com.elliot.tinyurl.util.URLShortener;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.buf.HexUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@RequiredArgsConstructor
public class Sha256URLShortener implements URLShortener {

    private final String baseUrl;

    private final MessageDigest md;

    @Override
    public String shortenUrl(String url) {
        byte[] hashBytes = md.digest(url.getBytes(StandardCharsets.UTF_8));
        return baseUrl + HexUtils.toHexString(hashBytes).substring(0, 10);
    }
}
