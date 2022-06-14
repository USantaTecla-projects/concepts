package com.example.backend.api.core.concept;

import com.example.backend.api.core.concept.dto.ConceptDTO;
import com.example.backend.api.core.concept.model.Concept;
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

    @GetMapping("/{id}")
    public Concept findOne(@PathVariable final Long id) {
        return conceptsService.findOne(id);
    }

    @GetMapping("/")
    public Page<Concept> findAll(@RequestParam Integer page) {
        return conceptsService.findAll(page);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateOne(@PathVariable final Long id, @RequestBody final ConceptDTO conceptDTO) {
        conceptsService.updateOne(id, conceptDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeOne(@PathVariable final Long id) {
        conceptsService.removeOne(id);
    }

}
