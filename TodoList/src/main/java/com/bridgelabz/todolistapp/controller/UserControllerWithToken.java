package com.bridgelabz.todolistapp.controller;

import com.bridgelabz.todolistapp.model.Notes;
import com.bridgelabz.todolistapp.service.UserServiceWithToken;
import com.google.keepnotesclone.dto.NotesDTO;
//import com.google.keepnotesclone.model.Notes;
//import com.google.keepnotesclone.service.UserServiceWithToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class UserControllerWithToken {
    @Autowired
    private UserServiceWithToken userService;

    @PostMapping("/logout/{Token}")
    public ResponseEntity<?> userLogOut (@PathVariable String Token){
        userService.logOut(Token);
        return new ResponseEntity(Map.of("message", "User Logged Out Successfully."), HttpStatus.OK);
    }

    @PostMapping("/createNotes/{Token}")
    public ResponseEntity<?> createNotes (@PathVariable String Token, @RequestBody NotesDTO Notes) {
        userService.addNotes(Token, Notes);
        return new ResponseEntity(Map.of("message", "Notes created Successfully."), HttpStatus.OK);
    }

    @DeleteMapping("/removeNotes/{Token}/{id}")
    public ResponseEntity<?> deleteNotes (@PathVariable String Token, @PathVariable Integer id) {
        userService.removeNotes(Token, id);
        return new ResponseEntity(Map.of("message", "Notes deleted Successfully."), HttpStatus.OK);
    }

    @PutMapping("/archiveNotes/{Token}/{id}")
    public ResponseEntity<?> archiveNotes (@PathVariable String Token, @PathVariable Integer id) {
        return userService.archiveNotesButton(Token, id);
    }

    @PutMapping("/pinNotes/{Token}/{id}")
    public ResponseEntity<?> pinNotes (@PathVariable String Token,  @PathVariable Integer id) {
        return userService.pinNotesButton(Token, id);
    }

    @PutMapping("/trashNotes/{Token}/{id}")
    public ResponseEntity<?> trashNotes (@PathVariable String Token, @PathVariable Integer id) {
        return userService.trashNotesButton(Token, id);
    }

    @GetMapping("/getNotesPage/{Token}")
    public List<Notes> getNotesPage (@PathVariable String Token) {
        return userService.getAllNotes(Token);
    }

}
