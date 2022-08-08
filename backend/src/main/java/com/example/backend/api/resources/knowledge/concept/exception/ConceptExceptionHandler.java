package com.example.backend.api.resources.knowledge.concept.exception;

import com.example.backend.api.exception.dto.ExceptionDTO;
import com.example.backend.api.resources.knowledge.concept.exception.model.ConceptDTOBadRequestException;
import com.example.backend.api.resources.knowledge.concept.exception.model.ConceptNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.annotation.Priority;
import java.time.ZonedDateTime;

@ControllerAdvice
@Priority(1)
public class ConceptExceptionHandler {

    @ExceptionHandler(value = {ConceptDTOBadRequestException.class})
    public ResponseEntity<Object> handleConceptDTOBadRequestException(ConceptDTOBadRequestException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now());

        return new ResponseEntity<>(exceptionDTO,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConceptNotFoundException.class})
    public ResponseEntity<Object> handleConceptNotFoundException(ConceptNotFoundException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now());

        return new ResponseEntity<>(exceptionDTO,HttpStatus.NOT_FOUND);
    }
}
