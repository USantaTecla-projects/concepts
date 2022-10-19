package com.example.backend.api.resources.exam.domain.family.question.exception.specific;

public class NotEnoughDataException extends RuntimeException {
    public NotEnoughDataException(String message) {
        super(message);
    }
}
