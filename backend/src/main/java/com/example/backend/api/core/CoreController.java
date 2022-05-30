package com.example.backend.api.core;

import com.example.backend.api.core.services.concepts.IConceptsService;
import com.example.backend.api.core.services.question.IQuestionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
public class CoreController {

    private final IQuestionService questionService;
    private final IConceptsService conceptsService;

    public CoreController(IQuestionService questionService, IConceptsService conceptsService) {
        this.questionService = questionService;
        this.conceptsService = conceptsService;
    }
}
