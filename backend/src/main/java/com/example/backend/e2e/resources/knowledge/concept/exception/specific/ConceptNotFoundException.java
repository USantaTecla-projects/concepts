package com.example.backend.e2e.resources.knowledge.concept.exception.specific;

public class ConceptNotFoundException extends RuntimeException {
    public ConceptNotFoundException(String message) {
        super(message);
    }
}
