package com.elliot.tinyurl.controller;

import com.elliot.tinyurl.dto.request.URLRequestDTO;
import com.elliot.tinyurl.dto.response.URLResponseDTO;
import com.elliot.tinyurl.service.URLService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("shortener")
@RequiredArgsConstructor
public class URLShortenerController {

    private final URLService urlService;

    @PostMapping
    public ResponseEntity<URLResponseDTO> shortenUrl(
            @Valid @RequestBody URLRequestDTO urlRequestDTO
    ) throws Exception {
        URLResponseDTO urlResponseDTO = urlService.getUrl(urlRequestDTO);
        if (urlResponseDTO == null) {
            urlResponseDTO = urlService.shortenUrlAndSave(urlRequestDTO);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(urlResponseDTO);
    }
}
