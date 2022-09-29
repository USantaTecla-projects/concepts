package com.example.backend.api.resources.exam.module.question.exception;

import com.example.backend.api.exception.specific.ExceptionDTO;
import com.example.backend.api.resources.exam.module.question.exception.specific.NotEnoughDataException;
import com.example.backend.api.resources.exam.module.question.exception.specific.QuestionDTOBadRequestException;
import com.example.backend.api.resources.exam.module.question.exception.specific.QuestionNotFoundException;
import com.example.backend.api.resources.exam.module.question.exception.specific.QuestionTypeDetailsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.annotation.Priority;
import java.time.ZonedDateTime;

@ControllerAdvice
@Priority(1)
public class QuestionExceptionHandler {

    @ExceptionHandler(value = {QuestionDTOBadRequestException.class})
    public ResponseEntity<Object> handleConceptDTOBadRequestException(QuestionDTOBadRequestException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now());

        return new ResponseEntity<>(exceptionDTO,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {QuestionNotFoundException.class})
    public ResponseEntity<Object> handleConceptNotFoundException(QuestionNotFoundException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now());

        return new ResponseEntity<>(exceptionDTO,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {NotEnoughDataException.class})
    public ResponseEntity<Object> handleNotEnoughDataException(NotEnoughDataException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                exception.getMessage(),
                HttpStatus.FAILED_DEPENDENCY,
                ZonedDateTime.now());

        return new ResponseEntity<>(exceptionDTO,HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler(value = {QuestionTypeDetailsNotFoundException.class})
    public ResponseEntity<Object> handleQuestionTypeDetailNotFoundException(QuestionTypeDetailsNotFoundException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now());

        return new ResponseEntity<>(exceptionDTO,HttpStatus.FAILED_DEPENDENCY);
    }
}
