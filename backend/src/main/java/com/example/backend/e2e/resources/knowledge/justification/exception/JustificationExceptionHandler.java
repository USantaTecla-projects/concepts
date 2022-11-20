package com.example.backend.e2e.resources.knowledge.justification.exception;


import com.example.backend.e2e.exception.specific.ExceptionDTO;
import com.example.backend.e2e.resources.knowledge.justification.exception.specific.JustificationDTOBadRequestException;
import com.example.backend.e2e.resources.knowledge.justification.exception.specific.JustificationErrorNotProvidedException;
import com.example.backend.e2e.resources.knowledge.justification.exception.specific.JustificationNotBelongToDefinitionException;
import com.example.backend.e2e.resources.knowledge.justification.exception.specific.JustificationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.annotation.Priority;
import java.time.ZonedDateTime;

@ControllerAdvice
@Priority(1)
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

    @ExceptionHandler(value = {JustificationNotBelongToDefinitionException.class})
    public ResponseEntity<Object> handleJustificationNotBelongToDefinitionsException(JustificationNotBelongToDefinitionException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now());

        return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }

}
