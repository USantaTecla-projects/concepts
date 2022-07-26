package com.example.backend.api.resources.knowledge.concept.exception.model;

public class ConceptDTOBadRequestException extends RuntimeException {
    public ConceptDTOBadRequestException(String message) {
        super(message);
    }
}
