package com.example.backend.api.core.answer.exception;

import com.example.backend.api.core.answer.exception.model.AnswerDTOBadRequestException;
import com.example.backend.api.core.answer.exception.model.AnswerNotBelongToConceptException;
import com.example.backend.api.core.answer.exception.model.AnswerNotFoundException;
import com.example.backend.api.core.concept.exception.model.ConceptDTOBadRequestException;
import com.example.backend.api.core.concept.exception.model.ConceptNotFoundException;
import com.example.backend.api.exception.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class AnswerExceptionHandler {

    @ExceptionHandler(value = {AnswerDTOBadRequestException.class})
    public ResponseEntity<Object> handleAnswerDTOBadRequestException(AnswerDTOBadRequestException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now());

        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {AnswerNotFoundException.class, AnswerNotBelongToConceptException.class})
    public ResponseEntity<Object> handleAnswerNotFoundException(AnswerNotFoundException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now());

        return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }
}
