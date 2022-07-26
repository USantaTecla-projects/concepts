package com.example.backend.api.resources.knowledge.justification.exception.model;

public class JustificationErrorNotProvidedException extends RuntimeException{
    public JustificationErrorNotProvidedException(String message) {
        super(message);
    }
}
