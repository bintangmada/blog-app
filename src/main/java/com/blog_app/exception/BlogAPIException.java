package com.blog_app.exception;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends RuntimeException{

    private HttpStatus status;
    private String messsage;

    public BlogAPIException(HttpStatus status, String messsage) {
        this.status = status;
        this.messsage = messsage;
    }

    public BlogAPIException(String message, HttpStatus status, String messsage) {
        super(message);
        this.status = status;
        this.messsage = messsage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMesssage() {
        return messsage;
    }
}
