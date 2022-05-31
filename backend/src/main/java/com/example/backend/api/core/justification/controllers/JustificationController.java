package com.example.backend.api.core.justification.controllers;

import com.example.backend.api.core.concepts.services.IConceptsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
public class JustificationController {
    private final IConceptsService justificationsService;

    public JustificationController(IConceptsService justificationsService) {
        this.justificationsService = justificationsService;
    }
}
