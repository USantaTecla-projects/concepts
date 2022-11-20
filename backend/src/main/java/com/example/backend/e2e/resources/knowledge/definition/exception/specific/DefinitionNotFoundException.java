package com.example.backend.e2e.resources.knowledge.definition.exception.specific;

public class DefinitionNotFoundException extends RuntimeException{

    public DefinitionNotFoundException(String message) {
        super(message);
    }

}
