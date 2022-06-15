package com.example.backend.api.resources.core.answer.exception.model;

public class AnswerNotFoundException extends RuntimeException{

    public AnswerNotFoundException(String message) {
        super(message);
    }

}
