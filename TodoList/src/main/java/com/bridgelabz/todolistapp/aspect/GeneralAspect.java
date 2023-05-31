package com.bridgelabz.todolistapp.aspect;

import com.bridgelabz.todolistapp.model.User;
import com.bridgelabz.todolistapp.repository.UserRepo;
import com.bridgelabz.todolistapp.utility.JWTToken;
import com.google.keepnotesclone.Exception.Exception;
import com.google.keepnotesclone.dto.UserLoginDTO;
//import com.google.keepnotesclone.model.User;
//import com.google.keepnotesclone.repository.UserRepo;
//import com.google.keepnotesclone.utility.JWTToken;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class GeneralAspect {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JWTToken jwtToken;

    @Before(value = "this(com.google.keepnotesclone.service.UserServiceWithToken)")
    public void around(JoinPoint joinPoint) throws Throwable {
        System.out.println("Started");
        String Token = (String) joinPoint.getArgs()[0];
        System.out.println(Token);
        if(Token.equals("null")) {
            throw new Exception("User Needs to Log in first.");
        }
        UserLoginDTO userLoginDetails = jwtToken.decodeToken(Token);
        User user = userRepo
                .findByEmail(userLoginDetails.getEmail())
                .orElseThrow(() -> new Exception("User not found."));
        if(user.getIsLogin() == Boolean.FALSE) {
            throw new Exception("User needs to Login first.");
        }
        System.out.println("ended");
    }

}