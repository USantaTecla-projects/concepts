package com.example.backend.api.resources.exam.exception;


import com.example.backend.api.exception.specific.ExceptionDTO;
import com.example.backend.api.resources.exam.exception.specific.CreateExamDTOBadRequestException;
import com.example.backend.api.resources.exam.exception.specific.ExamNotFoundException;
import com.example.backend.api.resources.exam.exception.specific.UpdateExamDTOBadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.annotation.Priority;
import java.time.ZonedDateTime;

@ControllerAdvice
@Priority(1)
public class ExamExceptionHandler {

    @ExceptionHandler(value = {CreateExamDTOBadRequestException.class})
    public ResponseEntity<Object> handleCreateExamDTOBadRequestException(CreateExamDTOBadRequestException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now());

        return new ResponseEntity<>(exceptionDTO,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UpdateExamDTOBadRequestException.class})
    public ResponseEntity<Object> handleReplyExamDTOBadRequestException(UpdateExamDTOBadRequestException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now());

        return new ResponseEntity<>(exceptionDTO,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ExamNotFoundException.class})
    public ResponseEntity<Object> handleExamNotFoundException(ExamNotFoundException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now());

        return new ResponseEntity<>(exceptionDTO,HttpStatus.BAD_REQUEST);
    }
}
