package com.example.backend.api.resources.knowledge.definition.exception;

import com.example.backend.api.exception.dto.ExceptionDTO;
import com.example.backend.api.resources.knowledge.definition.exception.model.DefinitionDTOBadRequestException;
import com.example.backend.api.resources.knowledge.definition.exception.model.DefinitionNotBelongToConceptException;
import com.example.backend.api.resources.knowledge.definition.exception.model.DefinitionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.annotation.Priority;
import java.time.ZonedDateTime;

@ControllerAdvice
@Priority(1)
public class DefinitionExceptionHandler {

    @ExceptionHandler(value = {DefinitionDTOBadRequestException.class})
    public ResponseEntity<Object> handleDefinitionDTOBadRequestException(DefinitionDTOBadRequestException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now());

        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {DefinitionNotFoundException.class})
    public ResponseEntity<Object> handleDefinitionNotFoundException(DefinitionNotFoundException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now());

        return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {DefinitionNotBelongToConceptException.class})
    public ResponseEntity<Object> handleDefinitionNotBelongToConceptException(DefinitionNotBelongToConceptException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now());

        return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }
}
