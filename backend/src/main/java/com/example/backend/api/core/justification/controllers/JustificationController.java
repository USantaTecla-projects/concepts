package com.example.backend.api.core.justification.controllers;

import com.example.backend.api.core.concept.IConceptService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
public class JustificationController {
    private final IConceptService justificationsService;

    public JustificationController(IConceptService justificationsService) {
        this.justificationsService = justificationsService;
    }
}
