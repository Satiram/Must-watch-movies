package com.example.Mustwatchmovies.user.controller;

import com.example.Mustwatchmovies.user.service.UserService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/mwm")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<?> saveUser(@RequestBody String body, HttpServletResponse response)  {
        try {
            JsonObject object = userService.saveUser(body);
            if(object.get("error").equals(true))
               return ResponseEntity.ok(object.get("message").getAsString());
            else
            return ResponseEntity.ok(object.get("message").getAsString());

        } catch (Exception e) {
            e.printStackTrace();
            return (ResponseEntity<Object>) ResponseEntity.internalServerError();
        }
    }
        @GetMapping("/login")
        public ResponseEntity<Object> loginUser(@RequestBody String body)
        {
//
            try {
                String object=userService.loginUser(body);
                return ResponseEntity.ok(object);

            }catch (Exception e)
            {
                e.printStackTrace();
                return (ResponseEntity<Object>) ResponseEntity.internalServerError();
            }

        }

    @GetMapping("/checkUsername")
    public ResponseEntity<Boolean> checkUsernameExists(@RequestParam String username) {
        boolean isExists = userService.isUsernameExists(username);
        return ResponseEntity.ok(isExists);
    }

}
