package com.example.Mustwatchmovies.user.service;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;


public interface UserService {
    ResponseEntity<String> saveUser(@RequestBody String body);

    ResponseEntity<String> loginUser(String body);
    boolean isUsernameExists(String username);
}
