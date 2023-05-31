package com.bridgelabz.todolistapp.repository;

import com.bridgelabz.todolistapp.model.NotesPage;
//import com.google.keepnotesclone.model.NotesPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotesPageRepo extends JpaRepository<NotesPage, Integer> {
}
