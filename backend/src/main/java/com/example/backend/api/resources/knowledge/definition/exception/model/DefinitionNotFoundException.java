package com.example.backend.api.resources.knowledge.definition.exception.model;

public class DefinitionNotFoundException extends RuntimeException{

    public DefinitionNotFoundException(String message) {
        super(message);
    }

}
