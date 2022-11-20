package com.example.backend.e2e.resources.knowledge.definition.exception.specific;

public class DefinitionDTOBadRequestException extends RuntimeException {
    public DefinitionDTOBadRequestException(String message) {
        super(message);
    }
}
