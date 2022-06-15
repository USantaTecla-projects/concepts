package com.example.backend.api.resources.core.concept.exception.model;

public class ConceptNotFoundException extends RuntimeException {
    public ConceptNotFoundException(String message) {
        super(message);
    }
}
