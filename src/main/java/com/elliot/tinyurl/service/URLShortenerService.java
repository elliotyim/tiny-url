package com.elliot.tinyurl.service;

import com.elliot.tinyurl.dto.request.URLRequestDTO;

public interface URLShortenerService {
    URLRequestDTO shortenUrl(URLRequestDTO urlRequestDTO) throws Exception;
}
