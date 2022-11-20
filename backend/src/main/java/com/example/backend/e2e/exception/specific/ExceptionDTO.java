package com.example.backend.e2e.exception.specific;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;


public record ExceptionDTO(String message, HttpStatus httpStatus, ZonedDateTime timeStamp) { }
