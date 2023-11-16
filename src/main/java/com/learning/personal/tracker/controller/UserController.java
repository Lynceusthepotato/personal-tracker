package com.learning.personal.tracker.controller;

import com.learning.personal.tracker.Constants;
import com.learning.personal.tracker.model.User;
import com.learning.personal.tracker.service.UserService;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestParam Map<String, Object> userMap) {
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        User user = userService.requestLogin(email, password);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestParam Map<String, Object> userMap) {
        String email = (String) userMap.get("email");
        String username = (String) userMap.get("username");
        String password = (String) userMap.get("password");
        User user = userService.registerUser(email, username, password);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    private Map<String, String> generateJWTToken(User user) {
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(Constants.API_SECRET_KEY)
                .issuedAt(new Date(timestamp))
                .expiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                .claim("user_id", user.getUserId())
                .claim("email", user.getEmail())
                .claim("username", user.getUsername())
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }
}
