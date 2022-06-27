package com.elliot.tinyurl.service;

import com.elliot.tinyurl.dto.request.URLRequestDTO;
import com.elliot.tinyurl.dto.response.URLResponseDTO;

public interface URLService {
    URLResponseDTO getUrl(URLRequestDTO urlRequestDTO) throws Exception;

    URLResponseDTO shortenUrlAndSave(URLRequestDTO urlRequestDTO);
}
