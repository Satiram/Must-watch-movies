package com.example.Mustwatchmovies.user.serviceImpl;

import com.example.Mustwatchmovies.user.models.UserInfo;
import com.example.Mustwatchmovies.user.repositories.UserInfoRepository;
import com.example.Mustwatchmovies.user.service.UserService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<String> saveUser(@RequestBody String body)
    {
        UserInfo userInfo=new UserInfo();
        JsonObject object=new JsonObject();
        JsonObject jsonObject=new JsonParser().parse(body).getAsJsonObject();
        String password=new BCryptPasswordEncoder().encode(jsonObject.get("password").getAsString());
       // String password=jsonObject.get("password").getAsString();
        String userId=jsonObject.get("userId").getAsString();
        String userName=jsonObject.get("userName").getAsString();
        try {
            UserInfo userInfo1=userInfoRepository.findByUserId(userId);
            if(Objects.nonNull(userInfo1))
                return new ResponseEntity<>("Account already exist with this email"
                , HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {

        }

        userInfo.setPassword(password);
        userInfo.setUserId(userId);
        userInfo.setUserName(userName);
        userInfoRepository.save(userInfo);
        object.addProperty("error",false);
        object.addProperty("message","saved successfully");
        return new ResponseEntity<>("Data successfully saved.", HttpStatus.OK);


    }
    public ResponseEntity<String> loginUser(@RequestBody String body){
        JsonObject jsonObject=new JsonParser().parse(body).getAsJsonObject();
        String userName=jsonObject.get("userId").getAsString();
        String password=jsonObject.get("password").getAsString();
        if (authenticateUser(userName,password))
             return new ResponseEntity<String>("Login successfully!", HttpStatus.OK);
        else return new ResponseEntity<String>("Email or Password doesn't match", HttpStatus.BAD_REQUEST);
    }
    public boolean authenticateUser(String userId, String rawpassword) {
        UserInfo user = userInfoRepository.findByUserId(userId);
        String password=user.getPassword();
//        if (user != null &&rawpassword.equals(password)) {
//            return true; // Authenticated succsessfully
//        }
        if (user != null && passwordEncoder.matches(rawpassword, user.getPassword())) {
            return true; // Authentication successful
        }
        return false; // Authentication failed
    }

    public boolean isUsernameExists(String username) {
        return userInfoRepository.existsByUsername(username);
    }
}
