package com.example.backend.api.resources.exam.exception.model;

public class CreateExamDTOBadRequestException extends RuntimeException{

    public CreateExamDTOBadRequestException(String message) {
        super(message);
    }

}
