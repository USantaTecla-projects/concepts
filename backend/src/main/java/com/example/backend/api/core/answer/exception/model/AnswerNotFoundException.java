package com.example.backend.api.core.answer.exception.model;

public class AnswerNotFoundException extends RuntimeException{

    public AnswerNotFoundException(String message) {
        super(message);
    }

}
