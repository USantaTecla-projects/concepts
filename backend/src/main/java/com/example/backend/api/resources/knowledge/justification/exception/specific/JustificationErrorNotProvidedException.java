package com.example.backend.api.resources.knowledge.justification.exception.specific;

public class JustificationErrorNotProvidedException extends RuntimeException{
    public JustificationErrorNotProvidedException(String message) {
        super(message);
    }
}
