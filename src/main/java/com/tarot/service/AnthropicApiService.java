package com.tarot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class AnthropicApiService {
//    @Value("${anthropic.api.key}")
//    private String apiKey;
//
//    private final RestTemplate restTemplate;
//
//    public String sendRequest(String prompt) {
//        String url = "https://api.anthropic.com/v1/messages";
//        System.out.println("prompt:"+prompt);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("X-API-Key", apiKey);
//
//        String requestBody = String.format("{\"prompt\": \"%s\", \"model\": \"claude-3-5-sonnet-20240620\", \"max_tokens\": 100}", prompt);
//
//        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
//
//        return restTemplate.postForObject(url, request, String.class);
//    }
}
