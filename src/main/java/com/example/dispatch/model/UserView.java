package com.example.dispatch.model;

import java.util.UUID;

import lombok.Data;

@Data
public class UserView {
    private UUID id;
    private String name;
    private String username;
    private String token;

    public UserView(){}

    public UserView(
        UUID id,
        String name,
        String username,
        String token
    ){
        this.id = id;
        this.name = name;
        this.username = username;
        this.token = token;
    }
}