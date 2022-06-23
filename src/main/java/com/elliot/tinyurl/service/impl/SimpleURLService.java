package com.elliot.tinyurl.service.impl;

import com.elliot.tinyurl.dto.request.URLRequestDTO;
import com.elliot.tinyurl.dto.response.URLResponseDTO;
import com.elliot.tinyurl.model.URL;
import com.elliot.tinyurl.repository.URLRepository;
import com.elliot.tinyurl.service.URLService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class SimpleURLService implements URLService {

    private final URLRepository urlRepository;

    @Override
    public URLResponseDTO getUrl(URLRequestDTO urlRequestDTO) throws Exception {
        Optional<URL> url = urlRepository.findByLongUrl(urlRequestDTO.getUrl());
        return url.isPresent() ? URLResponseDTO.of(url.get().getShortUrl()) : null;
    }

    @Override
    public URLResponseDTO saveUrl(URLRequestDTO urlResponseDTO) {
        URL savedUrl = urlRepository.save(
                URL.builder()
                        .longUrl(urlResponseDTO.getUrl())
                        .shortUrl(urlResponseDTO
                                .getShortUrl()).build());
        return URLResponseDTO.of(savedUrl.getShortUrl());
    }
}
