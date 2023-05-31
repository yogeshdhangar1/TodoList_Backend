package com.bridgelabz.todolistapp.controller;

import com.bridgelabz.todolistapp.service.UserServiceWithoutToken;
import com.google.keepnotesclone.dto.UserLoginDTO;
import com.google.keepnotesclone.dto.UserRegistrationDTO;
//import com.google.keepnotesclone.service.UserServiceWithoutToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin("*")
public class UserControllerWithoutToken {
    @Autowired
    private UserServiceWithoutToken userServiceWithoutToken;

    @PostMapping("/Registration")
    public ResponseEntity<?> userRegistration(@RequestBody UserRegistrationDTO user){
        userServiceWithoutToken.createUser(user);
        return new ResponseEntity(Map.of("message", "user Registered Successfully."), HttpStatus.OK);
    }

    @PostMapping("/login")
    public String userLogin(@RequestBody UserLoginDTO userLogin) {
        return userServiceWithoutToken.login(userLogin);
    }


}
