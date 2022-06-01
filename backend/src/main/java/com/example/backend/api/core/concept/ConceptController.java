package com.example.backend.api.core.concept;

import com.example.backend.api.core.concept.dto.ConceptDTO;
import com.example.backend.api.core.concept.model.Concept;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/concepts")
public class ConceptController {
    private final IConceptService conceptsService;

    public ConceptController(IConceptService conceptsService) {
        this.conceptsService = conceptsService;
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody Concept create(@RequestBody final ConceptDTO conceptDTO) {
        return this.conceptsService.create(conceptDTO);
    }

}
