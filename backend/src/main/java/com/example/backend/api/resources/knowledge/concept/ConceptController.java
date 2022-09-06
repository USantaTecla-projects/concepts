package com.example.backend.api.resources.knowledge.concept;

import com.example.backend.api.resources.knowledge.concept.dto.ConceptDTO;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/concepts")
public class ConceptController {

    private final ConceptService conceptsService;


    public ConceptController(ConceptService conceptsService) {
        this.conceptsService = conceptsService;
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Concept create(@RequestBody final ConceptDTO conceptDTO) {
        return conceptsService.create(conceptDTO);
    }

    @GetMapping("/{conceptId}")
    public Concept findOne(@PathVariable final Long conceptId) {
        return conceptsService.findOne(conceptId);
    }

    @GetMapping("/")
    public Page<Concept> findAll(@RequestParam Integer page) {
        return conceptsService.findAll(page);
    }

    @PutMapping("/{conceptId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateOne(@PathVariable final Long conceptId, @RequestBody final ConceptDTO conceptDTO) {
        conceptsService.updateOne(conceptId, conceptDTO);
    }

    @DeleteMapping("/{conceptId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeOne(@PathVariable final Long conceptId) {
        conceptsService.removeOne(conceptId);
    }

}
