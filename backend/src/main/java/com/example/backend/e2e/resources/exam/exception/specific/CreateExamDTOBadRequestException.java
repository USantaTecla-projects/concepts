package com.example.backend.e2e.resources.exam.exception.specific;

public class CreateExamDTOBadRequestException extends RuntimeException {

    public CreateExamDTOBadRequestException(String message) {
        super(message);
    }

}
