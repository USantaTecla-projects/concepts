package com.example.backend.api.resources.exam.domain.family.answer.exception.specific;

public class AnswerNotFoundException extends RuntimeException {

    public AnswerNotFoundException(String message) {
        super(message);
    }

}
