package com.example.dispatch.exception;

import java.util.ArrayList;
import java.util.List;

import com.example.dispatch.constant.ResponseCode;
import com.example.dispatch.response.ErrorResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandlingController extends ResponseEntityExceptionHandler {

    // A generic error occurred on the server
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> stackTraces = new ArrayList<>();
        stackTraces.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(ResponseCode.INTERNAL_SERVER_ERROR, "Internal Server Error",
                stackTraces);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Client sent an invalid request — such as lacking required request body or parameter
    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
        List<String> stackTraces = new ArrayList<>();
        stackTraces.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(ResponseCode.BAD_REQUEST, "Bad Request", stackTraces);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> stackTraces = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            String errorResult = fieldName + " " + errorMessage;
            stackTraces.add(errorResult);
        }
        ErrorResponse error = new ErrorResponse(ResponseCode.VALIDATION_FAILED, "Validation Failed", stackTraces);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // The requested resource does not exist
    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        List<String> stackTraces = new ArrayList<>();
        stackTraces.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(ResponseCode.NOT_FOUND, "Not Found", stackTraces);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public final ResponseEntity<Object> handleConflictException(ConflictException ex, WebRequest request) {
        List<String> stackTraces = new ArrayList<>();
        stackTraces.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(ResponseCode.CONFLICT, "Conflict", stackTraces);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}

