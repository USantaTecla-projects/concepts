package com.example.backend.api.resources.knowledge.definition.exception.specific;

public class DefinitionNotFoundException extends RuntimeException{

    public DefinitionNotFoundException(String message) {
        super(message);
    }

}
