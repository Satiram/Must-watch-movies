package com.example.Mustwatchmovies.user.service;


import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.RequestBody;


public interface UserService {
    JsonObject saveUser(@RequestBody String body);

    String loginUser(String body);
    boolean isUsernameExists(String username);
}
