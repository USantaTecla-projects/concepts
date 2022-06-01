package com.example.backend.api.exception.model.DTOBadRequest;

public class DTOBadRequest extends RuntimeException {
    public DTOBadRequest(String message) {
        super(message);
    }
}
