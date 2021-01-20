package com.example.dispatch.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Auth {
    
    @NotNull
    private String username;
    
    @NotNull
    @Size(min = 6)
    private String password;
}
