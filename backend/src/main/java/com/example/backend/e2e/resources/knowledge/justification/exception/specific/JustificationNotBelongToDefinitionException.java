package com.example.backend.e2e.resources.knowledge.justification.exception.specific;

public class JustificationNotBelongToDefinitionException extends RuntimeException {
    public JustificationNotBelongToDefinitionException(String message) {
        super(message);
    }
}
