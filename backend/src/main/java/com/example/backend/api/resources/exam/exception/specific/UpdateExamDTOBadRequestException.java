package com.example.backend.api.resources.exam.exception.specific;

public class UpdateExamDTOBadRequestException extends RuntimeException{

    public UpdateExamDTOBadRequestException(String message) {
        super(message);
    }

}
