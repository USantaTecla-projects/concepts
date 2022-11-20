package com.example.backend.e2e.resources.knowledge.concept.exception.specific;

public class ConceptDTOBadRequestException extends RuntimeException {
    public ConceptDTOBadRequestException(String message) {
        super(message);
    }
}
