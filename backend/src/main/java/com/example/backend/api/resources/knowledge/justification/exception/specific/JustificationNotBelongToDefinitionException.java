package com.example.backend.api.resources.knowledge.justification.exception.specific;

public class JustificationNotBelongToDefinitionException extends RuntimeException {
    public JustificationNotBelongToDefinitionException(String message) {
        super(message);
    }
}
