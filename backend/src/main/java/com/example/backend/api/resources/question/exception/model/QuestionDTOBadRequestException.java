package com.example.backend.api.resources.question.exception.model;

public class QuestionDTOBadRequestException extends RuntimeException {
    public QuestionDTOBadRequestException(String message) {
        super(message);
    }
}
