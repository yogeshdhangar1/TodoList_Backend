package com.bridgelabz.todolistapp.repository;

//import com.google.keepnotesclone.model.Notes;
import com.bridgelabz.todolistapp.model.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotesRepo extends JpaRepository<Notes, Integer> {
    @Query(value = "delete from Notes WHERE DATEDIFF(now(),`timestamp`) > 1", nativeQuery = true)
    public void deleteEntriesByTimeDifference();
}
