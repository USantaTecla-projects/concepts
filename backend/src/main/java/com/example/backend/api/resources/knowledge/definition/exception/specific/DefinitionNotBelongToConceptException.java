package com.example.backend.api.resources.knowledge.definition.exception.specific;

public class DefinitionNotBelongToConceptException extends RuntimeException{
    public DefinitionNotBelongToConceptException(String message) {
        super(message);
    }
}
