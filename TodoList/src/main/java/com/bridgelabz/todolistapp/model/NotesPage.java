package com.bridgelabz.todolistapp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
@Entity
public class NotesPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Notes> notes;
}
