package com.example.backend.api.core.justification.exception;


import com.example.backend.api.core.answer.exception.model.AnswerNotBelongToConceptException;
import com.example.backend.api.core.justification.exception.model.JustificationDTOBadRequestException;
import com.example.backend.api.core.justification.exception.model.JustificationErrorNotProvidedException;
import com.example.backend.api.core.justification.exception.model.JustificationNotBelongToAnswerException;
import com.example.backend.api.core.justification.exception.model.JustificationNotFoundException;
import com.example.backend.api.exception.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class JustificationExceptionHandler {

    @ExceptionHandler(value = {JustificationDTOBadRequestException.class})
    public ResponseEntity<Object> handleJustificationDTOBadRequestException(JustificationDTOBadRequestException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now());

        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {JustificationErrorNotProvidedException.class})
    public ResponseEntity<Object> handleJustificationDTOBadRequestException(JustificationErrorNotProvidedException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now());

        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {JustificationNotFoundException.class})
    public ResponseEntity<Object> handleJustificationNotFoundException(JustificationNotFoundException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now());

        return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {JustificationNotBelongToAnswerException.class})
    public ResponseEntity<Object> handleJustificationNotBelongToAnswerException(JustificationNotBelongToAnswerException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now());

        return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }

}