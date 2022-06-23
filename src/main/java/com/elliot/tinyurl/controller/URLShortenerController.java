package com.elliot.tinyurl.controller;

import com.elliot.tinyurl.dto.request.URLRequestDTO;
import com.elliot.tinyurl.dto.response.URLResponseDTO;
import com.elliot.tinyurl.service.URLService;
import com.elliot.tinyurl.service.URLShortenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("shortener")
@RequiredArgsConstructor
public class URLShortenerController {

    private final URLService urlService;

    private final URLShortenerService urlShortenerService;

    @PostMapping
    public ResponseEntity<URLResponseDTO> shortenUrl(@RequestBody URLRequestDTO urlRequestDTO) throws Exception {
        URLResponseDTO urlResponseDTO = urlService.getUrl(urlRequestDTO);
        if (urlResponseDTO == null) {
            urlRequestDTO = urlShortenerService.shortenUrl(urlRequestDTO);
            urlResponseDTO = urlService.saveUrl(urlRequestDTO);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(urlResponseDTO);
    }

}
