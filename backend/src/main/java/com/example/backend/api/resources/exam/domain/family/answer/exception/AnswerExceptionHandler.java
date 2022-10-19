package com.example.backend.api.resources.exam.domain.family.answer.exception;


import com.example.backend.api.exception.specific.ExceptionDTO;
import com.example.backend.api.resources.exam.domain.family.answer.exception.specific.AnswerDTOBadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.annotation.Priority;
import java.time.ZonedDateTime;

@ControllerAdvice
@Priority(1)
public class AnswerExceptionHandler {

    @ExceptionHandler(value = {AnswerDTOBadRequestException.class})
    public ResponseEntity<Object> handleAnswerDTOBadRequestException(AnswerDTOBadRequestException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now());

        return new ResponseEntity<>(exceptionDTO,HttpStatus.BAD_REQUEST);
    }
}
