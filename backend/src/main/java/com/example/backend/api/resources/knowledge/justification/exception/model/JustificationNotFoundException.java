package com.example.backend.api.resources.knowledge.justification.exception.model;

public class JustificationNotFoundException extends RuntimeException {
    public JustificationNotFoundException(String message) {
        super(message);
    }
}
