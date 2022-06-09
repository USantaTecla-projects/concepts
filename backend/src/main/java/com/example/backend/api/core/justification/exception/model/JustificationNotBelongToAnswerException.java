package com.example.backend.api.core.justification.exception.model;

public class JustificationNotBelongToAnswerException extends RuntimeException {
    public JustificationNotBelongToAnswerException(String message) {
        super(message);
    }
}
