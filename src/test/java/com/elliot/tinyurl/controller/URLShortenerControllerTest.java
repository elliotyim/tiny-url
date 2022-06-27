package com.elliot.tinyurl.controller;

import com.elliot.tinyurl.errors.ErrorCode;
import com.elliot.tinyurl.model.URL;
import com.elliot.tinyurl.repository.URLRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class URLShortenerControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private URLRepository urlRepository;


    @Test
    @DisplayName("URL 단축 - 정상")
    void shortenUrl() throws Exception {
        List<URL> previousUrls = urlRepository.findAll();

        Map<String, Object> payload = new HashMap<>();
        payload.put("url", "https://test-url.com");
        String body = objectMapper.writeValueAsString(payload);

        mockMvc.perform(
                post("/shortener").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.url").exists());

        List<URL> postUrls = urlRepository.findAll();

        assertEquals(previousUrls.size() + 1, postUrls.size());
    }

    @Test
    @DisplayName("URL 단축 - 1500자 이상")
    void requestWithTooLongUrl() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i< 1500; i++)
            sb.append("t");

        Map<String, Object> payload = new HashMap<>();
        payload.put("url", sb.toString());
        String body = objectMapper.writeValueAsString(payload);

        mockMvc.perform(
                post("/shortener").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.code").value(ErrorCode.INVALID_PARAMETER.getCode()));
    }

    @Test
    @DisplayName("URL 단축 - Request Body 없음")
    void requestWithoutRequestBody() throws Exception {
        mockMvc.perform(
                post("/shortener"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.code").value(ErrorCode.INVALID_PARAMETER.getCode()));
    }

    @Test
    @DisplayName("URL 단축 - url 공백")
    void requestWithBlankUrl() throws Exception {
        Map<String, Object> payload = new HashMap<>();
        String body = objectMapper.writeValueAsString(payload);

        mockMvc.perform(
                post("/shortener").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.code").value(ErrorCode.INVALID_PARAMETER.getCode()));

        payload.put("url", "");
        body = objectMapper.writeValueAsString(payload);
        mockMvc.perform(
                post("/shortener").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.code").value(ErrorCode.INVALID_PARAMETER.getCode()));

        payload.put("url", "   ");
        body = objectMapper.writeValueAsString(payload);
        mockMvc.perform(
                post("/shortener").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.code").value(ErrorCode.INVALID_PARAMETER.getCode()));
    }

}