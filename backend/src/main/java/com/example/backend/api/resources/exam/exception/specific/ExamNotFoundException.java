package com.example.backend.api.resources.exam.exception.specific;

public class ExamNotFoundException extends RuntimeException {

    public ExamNotFoundException(String message) {
        super(message);
    }

}
