package com.example.backend.api.core.justification.exception.model;

public class JustificationErrorNotProvidedException extends RuntimeException{
    public JustificationErrorNotProvidedException(String message) {
        super(message);
    }
}
