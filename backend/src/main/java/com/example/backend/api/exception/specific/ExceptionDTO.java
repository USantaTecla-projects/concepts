package com.example.backend.api.exception.specific;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;


public record ExceptionDTO(String message, HttpStatus httpStatus, ZonedDateTime timeStamp) { }
