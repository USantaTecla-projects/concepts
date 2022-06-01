package com.example.backend.api.exception.model.DTOBadRequest;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;


public record DTOBadRequestInfo(String message, HttpStatus httpStatus, ZonedDateTime timeStamp) { }
