package com.google.keepnotesclone.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserLoginDTO {
    private String email;
    private String password;
}
