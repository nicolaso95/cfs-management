package com.example.dispatch.response;

import java.util.List;

import lombok.Data;

@Data
public class ErrorResponse {
    private int code;
    private String message;
    private List<String> details;

    public ErrorResponse(int code, String message, List<String> details) {
        super();
        this.code = code;
        this.message = message;
        this.details = details;
    }
}