package com.bridgelabz.todolistapp.service;

import com.bridgelabz.todolistapp.model.Notes;
import com.bridgelabz.todolistapp.model.NotesPage;
import com.bridgelabz.todolistapp.model.User;
import com.bridgelabz.todolistapp.repository.NotesPageRepo;
import com.bridgelabz.todolistapp.repository.NotesRepo;
import com.bridgelabz.todolistapp.repository.UserRepo;
import com.bridgelabz.todolistapp.utility.JWTToken;
import com.google.keepnotesclone.Exception.Exception;
import com.google.keepnotesclone.dto.NotesDTO;
import com.google.keepnotesclone.dto.UserLoginDTO;
//import com.google.keepnotesclone.model.Notes;
//import com.google.keepnotesclone.model.NotesPage;
//import com.google.keepnotesclone.model.User;
//import com.google.keepnotesclone.repository.NotesPageRepo;
//import com.google.keepnotesclone.repository.NotesRepo;
//import com.google.keepnotesclone.repository.UserRepo;
//import com.google.keepnotesclone.utility.JWTToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceWithToken {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private NotesPageRepo notesPageRepo;
    @Autowired
    private NotesRepo notesRepo;
    @Autowired
    private JWTToken jwtToken;
    @Autowired
    private ModelMapper modelMapper;

    String msg = "";
    UserLoginDTO userLoginDetails;

    public void logOut(String Token) {
        User user = userRepo
                .findByEmail(jwtToken.decodeToken(Token).getEmail()).get();
        if(user.getIsLogin()) {
            user.setIsLogin(Boolean.FALSE);
            userRepo.save(user);
        }
    }

    public void addNotes(String Token, NotesDTO notes) {
        NotesPage notesPage = userRepo
                .findByEmail(jwtToken.decodeToken(Token).getEmail())
                .get()
                .getNotesPage();
        Notes notesUpdated = modelMapper.map(notes, Notes.class);
        notesUpdated.setPinNote(Boolean.FALSE);
        notesUpdated.setTrash(Boolean.FALSE);
        notesUpdated.setArchive(Boolean.FALSE);
        notesPage.getNotes().add(notesUpdated);
        notesPageRepo.save(notesPage);
    }
    public void removeNotes(String Token, Integer id) {
        NotesPage notesPage = userRepo
                .findByEmail(jwtToken.decodeToken(Token).getEmail())
                .get()
                .getNotesPage();
        Notes notesFound = notesPage.getNotes().stream().filter(a -> a.getId().equals(id))
                .findAny().orElseThrow(() -> new Exception("Note not found."));
        if(notesFound.getTrash() == Boolean.TRUE) {
            notesPage.getNotes().remove(notesFound);
            notesRepo.deleteById(id);
            notesPageRepo.save(notesPage);
        }
        else {
            throw new Exception("Note must be Trashed first.");
        }
    }

    public ResponseEntity<?> archiveNotesButton(String Token, Integer id) {
        NotesPage notesPage = userRepo
                .findByEmail(jwtToken.decodeToken(Token).getEmail())
                .get()
                .getNotesPage();
        Notes notes = notesPage.getNotes().stream().filter(a -> a.getId().equals(id))
                .findAny().orElseThrow(() -> new Exception("Note not found."));
        if(notes.getArchive() == Boolean.TRUE) {
            notes.setArchive(Boolean.FALSE);
            msg = "Notes un-archived Successfully";
        }
        else {
            notes.setArchive(Boolean.TRUE);
            msg = "Notes archived Successfully";
        }
        notesRepo.save(notes);
        return new ResponseEntity(Map.of("message", msg), HttpStatus.OK);
    }

    public ResponseEntity<?> pinNotesButton(String Token, Integer id) {
        NotesPage notesPage = userRepo
                .findByEmail(jwtToken.decodeToken(Token).getEmail())
                .get()
                .getNotesPage();
        Notes notes = notesPage.getNotes().stream().filter(a -> a.getId().equals(id))
                .findAny().orElseThrow(() -> new Exception("Note not found."));
        if(notes.getPinNote() == Boolean.TRUE) {
            notes.setPinNote(Boolean.FALSE);
            msg = "Notes un-pinned Successfully";
        }
        else {
            notes.setPinNote(Boolean.TRUE);
            msg = "Notes pinned Successfully";
        }
        notesRepo.save(notes);
        return new ResponseEntity(Map.of("message", msg), HttpStatus.OK);
    }

    public ResponseEntity<?> trashNotesButton(String Token, Integer id) {
        NotesPage notesPage = userRepo
                .findByEmail(jwtToken.decodeToken(Token).getEmail())
                .get()
                .getNotesPage();
        Notes notes = notesPage.getNotes().stream().filter(a -> a.getId().equals(id))
                .findAny().orElseThrow(() -> new Exception("Note not found."));
        if(notes.getTrash() == Boolean.TRUE) {
            notes.setTrash(Boolean.FALSE);
            notes.setTrashTime(null);
            msg = "Notes restored from Trash";
        }
        else {
            notes.setTrash(Boolean.TRUE);
            notes.setTrashTime(Date.from(Instant.now()));
            msg = "Notes added to Trash";
        }
        notesRepo.save(notes);
        return new ResponseEntity(Map.of("message", msg), HttpStatus.OK);
    }

    public List<Notes> getAllNotes(String Token) {
        userLoginDetails = jwtToken.decodeToken(Token);
        return userRepo.findByEmail(userLoginDetails.getEmail()).get().getNotesPage().getNotes();
    }

}
