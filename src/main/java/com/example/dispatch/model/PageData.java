package com.example.dispatch.model;

import lombok.Data;

@Data
public class PageData {
    private int limit;
    private int page;
    private int totalPages;
    private Object data;
}
