package com.example.backend.e2e.resources.knowledge.justification.exception.specific;

public class JustificationErrorNotProvidedException extends RuntimeException{
    public JustificationErrorNotProvidedException(String message) {
        super(message);
    }
}
