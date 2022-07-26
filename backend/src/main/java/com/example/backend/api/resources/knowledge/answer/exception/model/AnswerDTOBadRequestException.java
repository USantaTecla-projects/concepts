package com.example.backend.api.resources.knowledge.answer.exception.model;

public class AnswerDTOBadRequestException extends RuntimeException {
    public AnswerDTOBadRequestException(String message) {
        super(message);
    }
}
