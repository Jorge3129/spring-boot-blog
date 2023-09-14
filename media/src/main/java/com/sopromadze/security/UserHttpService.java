package com.sopromadze.security;

import com.sopromadze.model.user.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class UserHttpService {

  private final String baseUrl =  "http://localhost:8070/api/users/";

  public Long getUserIdByUsername(String username) {
    RestTemplate restTemplate = new RestTemplate();

    String url = baseUrl +"/id-by-username/" + username;

    return restTemplate.getForObject(url, Long.class);
  }

  public Optional<User> findByUsernameOrEmail(String username, String email) {
    RestTemplate restTemplate = new RestTemplate();

    String url = baseUrl +"/get-by-username/" + username;

    try {
      User user = restTemplate.getForObject(url, User.class);

      return Optional.ofNullable(user);
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  public Optional<User> findById(Long id) {
    RestTemplate restTemplate = new RestTemplate();

    String url = baseUrl +"/get-by-id/" + id;

    try {
      User user = restTemplate.getForObject(url, User.class);

      return Optional.ofNullable(user);
    } catch (Exception e) {
      return Optional.empty();
    }
  }
}
