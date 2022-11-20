package com.example.backend.e2e.resources.exam.exception.specific;

public class ExamNotFoundException extends RuntimeException {

    public ExamNotFoundException(String message) {
        super(message);
    }

}
