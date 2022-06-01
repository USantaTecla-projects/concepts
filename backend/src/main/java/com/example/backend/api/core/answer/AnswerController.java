package com.example.backend.api.core.answer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
public class AnswerController {
    private final IAnswerService answersService;

    public AnswerController(IAnswerService answersService) {
        this.answersService = answersService;
    }
}
