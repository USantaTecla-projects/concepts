package com.example.backend.api.resources.core.concept.exception.model;

public class ConceptDTOBadRequestException extends RuntimeException {
    public ConceptDTOBadRequestException(String message) {
        super(message);
    }
}
