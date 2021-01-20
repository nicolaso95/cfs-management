package com.example.dispatch.constant;

public class ResponseCode {

    // 200
    public static final int SUCCESS = 20000;

    // 400
    public static final int BAD_REQUEST = 40000;
    public static final int VALIDATION_FAILED = 40001;

    // 401
    public static final int UNAUTHORIZED = 40100;   
    public static final int ACCESS_DENIED = 40101;

    // 404
    public static final int NOT_FOUND = 40400;

    // 409
    public static final int CONFLICT = 40900;

    // 500
    public static final int INTERNAL_SERVER_ERROR = 50000;

}
