package com.example.backend.api.exception.dto;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;


public record ExceptionDTO(String message, HttpStatus httpStatus, ZonedDateTime timeStamp) { }
