package com.example.backend.api.resources.exam.resources.question.exception.model;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(String message) {
        super(message);
    }
}
