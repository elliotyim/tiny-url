package com.elliot.tinyurl.config;

import com.elliot.tinyurl.repository.URLRepository;
import com.elliot.tinyurl.service.URLService;
import com.elliot.tinyurl.service.impl.SimpleURLService;
import com.elliot.tinyurl.util.URLShortener;
import com.elliot.tinyurl.util.impl.Sha256URLShortener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Configuration
public class AppConfig {

    @Value("${shortener.baseUrl:https://tiny-url.test.com/}")
    private String baseUrl;

    @Bean
    public MessageDigest messageDigest() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("SHA3-256");
    }

    @Bean
    public String baseUrl() {
        return baseUrl;
    }

    @Bean
    public URLShortener urlShortener(MessageDigest md) {
        return new Sha256URLShortener(baseUrl, md);
    }

    @Bean
    public URLService urlService(URLRepository urlRepository, URLShortener urlShortener) {
        return new SimpleURLService(urlRepository, urlShortener);
    }
}
