package com.example.backend.api.resources.exam.exception;


import com.example.backend.api.exception.dto.ExceptionDTO;
import com.example.backend.api.resources.exam.exception.model.CreateExamDTOBadRequestException;
import com.example.backend.api.resources.exam.exception.model.ReplyExamDTOBadRequestException;
import com.example.backend.api.resources.exam.resources.question.exception.model.QuestionDTOBadRequestException;
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

    @ExceptionHandler(value = {ReplyExamDTOBadRequestException.class})
    public ResponseEntity<Object> handleReplyExamDTOBadRequestException(ReplyExamDTOBadRequestException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now());

        return new ResponseEntity<>(exceptionDTO,HttpStatus.BAD_REQUEST);
    }
}
