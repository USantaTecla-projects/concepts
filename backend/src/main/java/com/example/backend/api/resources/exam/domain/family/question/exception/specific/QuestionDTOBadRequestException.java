package com.example.backend.api.resources.exam.domain.family.question.exception.specific;

public class QuestionDTOBadRequestException extends RuntimeException {
    public QuestionDTOBadRequestException(String message) {
        super(message);
    }
}
