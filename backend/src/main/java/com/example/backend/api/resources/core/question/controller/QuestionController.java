package com.example.backend.api.resources.core.question.controller;

import com.example.backend.api.resources.core.question.services.IQuestionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final IQuestionService questionService;


    public QuestionController(IQuestionService questionService) {
        this.questionService = questionService;
    }
}
