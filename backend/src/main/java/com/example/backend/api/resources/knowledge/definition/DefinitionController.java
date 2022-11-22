package com.example.backend.api.resources.knowledge.definition;

import com.example.backend.api.resources.knowledge.definition.dto.DefinitionDTO;
import com.example.backend.api.resources.knowledge.definition.model.Definition;
import com.example.backend.api.resources.knowledge.concept.CrudConceptService;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concepts/{conceptID}/definitions")
public class DefinitionController {

    private final CrudConceptService crudConceptService;
    private final CrudDefinitionService crudDefinitionService;


    public DefinitionController(
            CrudConceptService crudConceptService,
            CrudDefinitionService crudDefinitionService
    ) {
        this.crudConceptService = crudConceptService;
        this.crudDefinitionService = crudDefinitionService;
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Definition createOne(
            @PathVariable final Long conceptID,
            @RequestBody final DefinitionDTO definitionDTO
    ) {
        Concept concept = crudConceptService.findOne(conceptID);
        return crudDefinitionService.createOne(concept, definitionDTO);

    }

    @GetMapping("/{definitionID}")
    public Definition findOne(
            @PathVariable final Long conceptID,
            @PathVariable final Long definitionID
    ) {
        Concept concept = crudConceptService.findOne(conceptID);
        return crudDefinitionService.findOne(concept, definitionID);
    }

    @GetMapping("/")
    public List<Definition> findAll(
            @PathVariable final Long conceptID
    ) {
        Concept concept = crudConceptService.findOne(conceptID);
        return crudDefinitionService.findAll(concept);
    }

    @PutMapping("/{definitionID}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateOne(
            @PathVariable final Long conceptID,
            @PathVariable final Long definitionID,
            @RequestBody final DefinitionDTO definitionDTO
    ) {
        Concept concept = crudConceptService.findOne(conceptID);
        crudDefinitionService.updateOne(concept, definitionID, definitionDTO);
    }

    @DeleteMapping("/{definitionID}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeOne(
            @PathVariable final Long conceptID,
            @PathVariable final Long definitionID
    ) {
        Concept concept = crudConceptService.findOne(conceptID);
        crudDefinitionService.removeOne(concept, definitionID);
    }
}
