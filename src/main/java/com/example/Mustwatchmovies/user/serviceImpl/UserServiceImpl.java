package com.example.Mustwatchmovies.user.serviceImpl;

import com.example.Mustwatchmovies.user.models.UserInfo;
import com.example.Mustwatchmovies.user.repositories.UserInfoRepository;
import com.example.Mustwatchmovies.user.service.UserService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public JsonObject saveUser(@RequestBody String body)
    {
        UserInfo userInfo=new UserInfo();
        JsonObject object=new JsonObject();
        JsonObject jsonObject=new JsonParser().parse(body).getAsJsonObject();
        String password=new BCryptPasswordEncoder().encode(jsonObject.get("password").getAsString());
       // String password=jsonObject.get("password").getAsString();
        String userId=jsonObject.get("userId").getAsString();
        String userName=jsonObject.get("userName").getAsString();
        Boolean isUserExist= isUsernameExists( userName);
        if(isUserExist) {
            object.addProperty("error", true);
            object.addProperty("message", "UserName Already exists");
            return  object;
        }
        userInfo.setPassword(password);
        userInfo.setUserId(userId);
        userInfo.setUserName(userName);
        userInfoRepository.save(userInfo);
        object.addProperty("error",false);
        object.addProperty("message","saved successfully");
        return object;


    }
    public  String loginUser(@RequestBody String body){
        JsonObject jsonObject=new JsonParser().parse(body).getAsJsonObject();
        String userName=jsonObject.get("userName").getAsString();
        String password=jsonObject.get("password").getAsString();
        if (authenticateUser(userName,password))
            return "logged in successfully";
        else return "username or password does not match";
    }
    public boolean authenticateUser(String username, String rawpassword) {
        UserInfo user = userInfoRepository.findByUsername(username);
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
