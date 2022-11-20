package com.example.backend.e2e.resources.knowledge.justification.exception.specific;

public class JustificationNotFoundException extends RuntimeException {
    public JustificationNotFoundException(String message) {
        super(message);
    }
}
