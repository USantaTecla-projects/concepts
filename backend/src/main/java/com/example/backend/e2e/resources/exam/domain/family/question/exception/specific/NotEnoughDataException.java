package com.example.backend.e2e.resources.exam.domain.family.question.exception.specific;

public class NotEnoughDataException extends RuntimeException {
    public NotEnoughDataException(String message) {
        super(message);
    }
}
