package com.elliot.tinyurl.controller;

import com.elliot.tinyurl.dto.request.URLRequestDTO;
import com.elliot.tinyurl.dto.response.URLResponseDTO;
import com.elliot.tinyurl.model.URL;
import com.elliot.tinyurl.repository.URLRepository;
import com.elliot.tinyurl.service.URLService;
import com.elliot.tinyurl.service.URLShortenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("shortener")
@RequiredArgsConstructor
public class URLShortenerController {

    private final URLService urlService;

    private final URLShortenerService urlShortenerService;

    private final URLRepository urlRepository;

    @PostMapping
    public ResponseEntity<URLResponseDTO> shortenUrl(
            @Valid @RequestBody URLRequestDTO urlRequestDTO
    ) throws Exception {
        URLResponseDTO urlResponseDTO = urlService.getUrl(urlRequestDTO);
        if (urlResponseDTO == null) {
            urlRequestDTO = urlShortenerService.shortenUrl(urlRequestDTO);
            urlResponseDTO = urlService.saveUrl(urlRequestDTO);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(urlResponseDTO);
    }

    @GetMapping("urls")
    public ResponseEntity<Object> getUrls() throws Exception {
        List<URL> urls = urlRepository.findAll();
        return ResponseEntity.ok(urls);
    }

}
