package com.example.backend.api.resources.knowledge.concept.exception.specific;

public class ConceptNotFoundException extends RuntimeException {
    public ConceptNotFoundException(String message) {
        super(message);
    }
}
