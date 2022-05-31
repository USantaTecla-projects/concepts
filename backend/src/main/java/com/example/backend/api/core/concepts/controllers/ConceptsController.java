package com.example.backend.api.core.concepts.controllers;

import com.example.backend.api.core.concepts.services.IConceptsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
public class ConceptsController {
    private final IConceptsService conceptsService;

    public ConceptsController(IConceptsService conceptsService) {
        this.conceptsService = conceptsService;
    }
}
