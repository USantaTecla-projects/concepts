package com.example.backend.api.exception;

import com.example.backend.api.exception.model.DTOBadRequest.DTOBadRequest;
import com.example.backend.api.exception.model.DTOBadRequest.DTOBadRequestInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {DTOBadRequest.class})
    public ResponseEntity<Object> handleDTOBadRequestException(DTOBadRequest exception){
        DTOBadRequestInfo DTOBadRequestInfo = new DTOBadRequestInfo(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now());

        return new ResponseEntity<>(DTOBadRequestInfo,HttpStatus.BAD_REQUEST);
    }
}
