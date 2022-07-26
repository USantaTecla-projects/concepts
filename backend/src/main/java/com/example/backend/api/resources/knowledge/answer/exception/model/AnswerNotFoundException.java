package com.example.backend.api.resources.knowledge.answer.exception.model;

public class AnswerNotFoundException extends RuntimeException{

    public AnswerNotFoundException(String message) {
        super(message);
    }

}
