package com.example.backend.api.resources.knowledge.concept;

import com.example.backend.api.resources.knowledge.concept.dto.ConceptDTO;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/concepts")
public class ConceptController {

    private final CrudConceptService crudConceptsService;

    public ConceptController(CrudConceptService crudConceptsService) {
        this.crudConceptsService = crudConceptsService;
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Concept createOne(@RequestBody final ConceptDTO conceptDTO) {
        return crudConceptsService.createOne(conceptDTO);
    }

    @GetMapping("/{conceptID}")
    public Concept findOne(@PathVariable final Long conceptID) {
        return crudConceptsService.findOne(conceptID);
    }

    @GetMapping("/count")
    public Long count() {
        return crudConceptsService.count();
    }

    @GetMapping("/")
    public Page<Concept> findAll(@RequestParam Integer page) {
        return crudConceptsService.findAll(page);
    }

    @PutMapping("/{conceptID}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateOne(@PathVariable final Long conceptID, @RequestBody final ConceptDTO conceptDTO) {
        crudConceptsService.updateOne(conceptID, conceptDTO);
    }

    @DeleteMapping("/{conceptID}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeOne(@PathVariable final Long conceptID) {
        crudConceptsService.removeOne(conceptID);
    }

}
