package com.sopromadze.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PostHttpService {

  private final String baseUrl = "http://localhost:8080/api/posts/";

  public Long countByCreatedBy(Long userId) {
    RestTemplate restTemplate = new RestTemplate();

    String url = baseUrl +"/count-by-created-by/" + userId;

    return restTemplate.getForObject(url, Long.class);
  }
}
