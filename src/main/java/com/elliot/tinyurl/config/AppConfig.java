package com.elliot.tinyurl.config;

import com.elliot.tinyurl.repository.URLRepository;
import com.elliot.tinyurl.service.URLService;
import com.elliot.tinyurl.service.URLShortenerService;
import com.elliot.tinyurl.service.impl.Sha256URLShortenerService;
import com.elliot.tinyurl.service.impl.SimpleURLService;
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
    public URLService urlService(URLRepository urlRepository) {
        return new SimpleURLService(urlRepository);
    }

    @Bean
    public URLShortenerService urlShortenerService(MessageDigest md) {
        return new Sha256URLShortenerService(baseUrl, md);
    }
}
