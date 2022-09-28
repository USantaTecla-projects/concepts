package com.example.backend.api.resources.knowledge.definition.exception.model;

public class DefinitionDTOBadRequestException extends RuntimeException {
    public DefinitionDTOBadRequestException(String message) {
        super(message);
    }
}
