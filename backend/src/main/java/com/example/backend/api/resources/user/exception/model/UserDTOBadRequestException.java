package com.example.backend.api.resources.user.exception.model;

public class UserDTOBadRequestException extends RuntimeException{

    public UserDTOBadRequestException(String message) {
        super(message);
    }
}
