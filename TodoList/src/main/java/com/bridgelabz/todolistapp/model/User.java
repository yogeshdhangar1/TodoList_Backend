package com.bridgelabz.todolistapp.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean isLogin;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "notespage_id")
    private NotesPage notesPage;
}
