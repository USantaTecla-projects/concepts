package com.example.backend.e2e.resources.user.exception.model;

public class UserDTOBadRequestException extends RuntimeException {
    public UserDTOBadRequestException(String message) {
        super(message);
    }
}
