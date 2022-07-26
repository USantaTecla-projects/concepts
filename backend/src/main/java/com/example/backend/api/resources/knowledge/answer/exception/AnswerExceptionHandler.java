package com.example.backend.api.resources.knowledge.answer.exception;

import com.example.backend.api.resources.knowledge.answer.exception.model.AnswerDTOBadRequestException;
import com.example.backend.api.resources.knowledge.answer.exception.model.AnswerNotBelongToConceptException;
import com.example.backend.api.resources.knowledge.answer.exception.model.AnswerNotFoundException;
import com.example.backend.api.exception.dto.ExceptionDTO;
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
    public ResponseEntity<Object> handleAnswerDTOBadRequestException(AnswerDTOBadRequestException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now());

        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {AnswerNotFoundException.class})
    public ResponseEntity<Object> handleAnswerNotFoundException(AnswerNotFoundException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now());

        return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {AnswerNotBelongToConceptException.class})
    public ResponseEntity<Object> handleAnswerNotBelongToConceptException(AnswerNotBelongToConceptException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now());

        return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }
}
