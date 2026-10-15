package com.example.employee_management.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateClient {

    private final RestTemplate restTemplate;
    public RestTemplateClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    // GET
    public <T> T get(String url, Class<T> responseType, String token) {
        HttpEntity<Void> request = new HttpEntity<>(createHeaders(token));
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, request, responseType);
        return response.getBody();
    }

    // POST
    public <T> T post(String url, Object requestData, Class<T> responseType, String token) {

        HttpEntity<Object> request = new HttpEntity<>(requestData, createHeaders(token));
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, request, responseType);
        return response.getBody();
    }

    // PUT
    public <T> T put(String url, Object requestData, Class<T> responseType, String token) {

        HttpEntity<Object> request = new HttpEntity<>(requestData, createHeaders(token));
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.PUT, request, responseType);
        return response.getBody();
    }

    // DELETE
    public void delete(String url, String token) {
        HttpEntity<Void> request = new HttpEntity<>(createHeaders(token));
        restTemplate.exchange(url, HttpMethod.DELETE, request, Void.class);
    }
}

