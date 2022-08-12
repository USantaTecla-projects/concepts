package com.example.backend.api.resources.exam.question.exception.model;

public class QuestionDTOBadRequestException extends RuntimeException {
    public QuestionDTOBadRequestException(String message) {
        super(message);
    }
}
