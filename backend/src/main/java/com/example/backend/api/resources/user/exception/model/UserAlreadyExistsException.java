package com.example.backend.api.resources.user.exception.model;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
