package com.example.Mustwatchmovies.user.controller;

import com.example.Mustwatchmovies.user.service.UserService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/mwm")
public class UserController {
    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "http://127.0.0.1:5500/")
    @PostMapping("/signup")
    public ResponseEntity<String> saveUser(@RequestBody String body ) {
        try {
            ResponseEntity<String> response = userService.saveUser(body);
//            if(object.get("error").equals("false"))
                return response;
//            else
//                return new ResponseEntity<>(object.get("message").getAsString(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            e.printStackTrace();
           return new  ResponseEntity<String>("internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500/")
        @PostMapping("/login")
        public ResponseEntity<String> loginUser(@RequestBody String body)
        {
            try {
                System.out.println("logg");
                ResponseEntity<String> response=userService.loginUser(body);
                return response;
            }catch (Exception e)
            {
                e.printStackTrace();
                return new  ResponseEntity<String>("internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }

    @GetMapping("/checkUsername")
    public ResponseEntity<Boolean> checkUsernameExists(@RequestParam String username) {
        boolean isExists = userService.isUsernameExists(username);
        return ResponseEntity.ok(isExists);
    }

}
