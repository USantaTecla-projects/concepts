package com.example.backend.api.resources.knowledge.justification.exception.model;

public class JustificationNotBelongToAnswerException extends RuntimeException {
    public JustificationNotBelongToAnswerException(String message) {
        super(message);
    }
}
