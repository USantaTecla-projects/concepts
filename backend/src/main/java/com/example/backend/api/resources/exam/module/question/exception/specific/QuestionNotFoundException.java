package com.example.backend.api.resources.exam.module.question.exception.specific;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(String message) {
        super(message);
    }
}
