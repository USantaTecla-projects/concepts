package com.example.backend.api.resources.knowledge.concept.exception.specific;

public class ConceptDTOBadRequestException extends RuntimeException {
    public ConceptDTOBadRequestException(String message) {
        super(message);
    }
}
