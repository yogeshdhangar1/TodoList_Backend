package com.bridgelabz.todolistapp.service;

import com.bridgelabz.todolistapp.model.NotesPage;
import com.bridgelabz.todolistapp.model.User;
import com.bridgelabz.todolistapp.repository.UserRepo;
import com.bridgelabz.todolistapp.utility.JWTToken;
import com.google.keepnotesclone.Exception.Exception;
import com.google.keepnotesclone.dto.UserLoginDTO;
import com.google.keepnotesclone.dto.UserRegistrationDTO;
//import com.google.keepnotesclone.model.NotesPage;
//import com.google.keepnotesclone.model.User;
//import com.google.keepnotesclone.repository.NotesPageRepo;
//import com.google.keepnotesclone.repository.NotesRepo;
//import com.google.keepnotesclone.repository.UserRepo;
//import com.google.keepnotesclone.utility.JWTToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceWithoutToken {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JWTToken jwtToken;
    @Autowired
    private ModelMapper modelMapper;


    public void createUser(UserRegistrationDTO user) {
        if(userRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("Email already exists");
        }
        User userMapped = modelMapper.map(user, User.class);
        userMapped.setNotesPage(new NotesPage());
        userMapped.setIsLogin(Boolean.FALSE);
        userRepo.save(userMapped);
    }

    public String login(UserLoginDTO userDetails) {
        User user = userRepo
                .findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new Exception("No User registered with : " + userDetails.getEmail()));
        if(!user.getPassword().equals(userDetails.getPassword())) {
            throw new Exception("Wrong User/Password.");
        }
        if(!user.getIsLogin()) {
            user.setIsLogin(Boolean.TRUE);
            userRepo.save(user);
        }
        else {
            throw new Exception("User already Logged in.");
        }

        return jwtToken.generateToken(userDetails);
    }
}
