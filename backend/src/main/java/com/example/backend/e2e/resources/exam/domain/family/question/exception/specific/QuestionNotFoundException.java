package com.example.backend.e2e.resources.exam.domain.family.question.exception.specific;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(String message) {
        super(message);
    }
}
