package com.elliot.tinyurl.service.impl;

import com.elliot.tinyurl.dto.request.URLRequestDTO;
import com.elliot.tinyurl.dto.response.URLResponseDTO;
import com.elliot.tinyurl.model.URL;
import com.elliot.tinyurl.repository.URLRepository;
import com.elliot.tinyurl.service.URLService;
import com.elliot.tinyurl.util.URLShortener;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
public class SimpleURLService implements URLService {

    private final URLRepository urlRepository;

    private final URLShortener urlShortener;

    @Override
    public URLResponseDTO getUrl(URLRequestDTO urlRequestDTO) throws Exception {
        Optional<URL> url = urlRepository.findByLongUrl(urlRequestDTO.getUrl());
        return url.isPresent() ? URLResponseDTO.of(url.get().getShortUrl()) : null;
    }

    @Override
    @Transactional
    public URLResponseDTO shortenUrlAndSave(URLRequestDTO urlRequestDTO) {
        String shortUrl = urlShortener.shortenUrl(urlRequestDTO.getUrl());
        URL savedUrl = urlRepository.save(
                URL.builder()
                        .longUrl(urlRequestDTO.getUrl())
                        .shortUrl(shortUrl).build());
        return URLResponseDTO.of(savedUrl.getShortUrl());
    }
}
