package com.example.backend.api.core.justification.exception.model;

public class JustificationNotFoundException extends RuntimeException {
    public JustificationNotFoundException(String message) {
        super(message);
    }
}
