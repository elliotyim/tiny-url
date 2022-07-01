package com.elliot.tinyurl.controller;

import com.elliot.tinyurl.BaseIntegrationTest;
import com.elliot.tinyurl.entity.rdb.URL;
import com.elliot.tinyurl.errors.ErrorCode;
import com.elliot.tinyurl.repository.rdb.URLRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import redis.embedded.RedisServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class URLShortenerControllerTest extends BaseIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private URLRepository urlRepository;

    private RedisServer redisServer;

    @BeforeAll
    void setup() {
        redisServer = new RedisServer(6379);
        redisServer.start();
    }

    @AfterAll
    void teardown() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }


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