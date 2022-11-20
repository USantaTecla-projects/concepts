package com.example.backend.e2e.resources.knowledge.definition.exception.specific;

public class DefinitionNotBelongToConceptException extends RuntimeException{
    public DefinitionNotBelongToConceptException(String message) {
        super(message);
    }
}
