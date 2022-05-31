package com.example.backend.api.core.answer.controllers;

import com.example.backend.api.core.answer.services.IAnswersService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
public class AnswersController {
    private final IAnswersService answersService;

    public AnswersController(IAnswersService answersService) {
        this.answersService = answersService;
    }
}
