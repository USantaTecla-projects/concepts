package com.example.backend.api.exception;

import com.example.backend.api.exception.dto.ExceptionDTO;
import com.example.backend.api.exception.model.DTOBadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {DTOBadRequestException.class})
    public ResponseEntity<Object> handleDTOBadRequestException(DTOBadRequestException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now());

        return new ResponseEntity<>(exceptionDTO,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now());

        return new ResponseEntity<>(exceptionDTO,HttpStatus.NOT_FOUND);
    }
}
