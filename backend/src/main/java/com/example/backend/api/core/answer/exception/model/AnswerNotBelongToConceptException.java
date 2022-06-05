package com.example.backend.api.core.answer.exception.model;

public class AnswerNotBelongToConceptException extends RuntimeException{

    public AnswerNotBelongToConceptException(String message) {
        super(message);
    }

}
