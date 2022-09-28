package com.example.backend.api.resources.exam.resources.question.exception.model;

public class QuestionDTOBadRequestException extends RuntimeException {
    public QuestionDTOBadRequestException(String message) {
        super(message);
    }
}
