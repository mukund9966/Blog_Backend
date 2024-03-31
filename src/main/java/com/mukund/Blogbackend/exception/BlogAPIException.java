package com.mukund.Blogbackend.exception;

import org.springframework.http.HttpStatus;
//use this exception while writing business logic or validate request parameters
public class BlogAPIException extends RuntimeException{
    private String message;
    private HttpStatus status;

    public BlogAPIException( HttpStatus status,String message) {
        this.message = message;
        this.status = status;
    }

    public BlogAPIException(String message, String message1, HttpStatus status) {
        super(message);
        this.message = message1;
        this.status = status;
    }
}
