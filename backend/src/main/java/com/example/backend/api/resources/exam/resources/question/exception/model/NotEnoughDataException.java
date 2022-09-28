package com.example.backend.api.resources.exam.resources.question.exception.model;

public class NotEnoughDataException extends RuntimeException {
    public NotEnoughDataException(String message) {
        super(message);
    }
}
