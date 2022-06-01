package com.example.backend.api.exception.model;

public class DTOBadRequestException extends RuntimeException {
    public DTOBadRequestException(String message) {
        super(message);
    }
}
