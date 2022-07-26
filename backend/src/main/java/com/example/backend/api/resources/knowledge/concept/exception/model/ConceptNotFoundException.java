package com.example.backend.api.resources.knowledge.concept.exception.model;

public class ConceptNotFoundException extends RuntimeException {
    public ConceptNotFoundException(String message) {
        super(message);
    }
}
