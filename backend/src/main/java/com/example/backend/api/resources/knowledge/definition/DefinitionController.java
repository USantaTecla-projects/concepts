package com.example.backend.api.resources.knowledge.definition;

import com.example.backend.api.resources.knowledge.definition.dto.DefinitionDTO;
import com.example.backend.api.resources.knowledge.definition.model.Definition;
import com.example.backend.api.resources.knowledge.concept.ConceptService;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concepts/{conceptID}/definitions")
public class DefinitionController {

    private final ConceptService conceptService;
    private final DefinitionService definitionService;


    public DefinitionController(
            ConceptService conceptService,
            DefinitionService definitionService
    ) {
        this.conceptService = conceptService;
        this.definitionService = definitionService;
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Definition create(
            @PathVariable final Long conceptID,
            @RequestBody final DefinitionDTO definitionDTO
    ) {
        Concept concept = conceptService.findOne(conceptID);
        return definitionService.create(concept, definitionDTO);

    }

    @GetMapping("/{definitionID}")
    public Definition findOne(
            @PathVariable final Long conceptID,
            @PathVariable final Long definitionID
    ) {
        Concept concept = conceptService.findOne(conceptID);
        return definitionService.findOne(concept, definitionID);
    }

    @GetMapping("/")
    public List<Definition> findAll(
            @PathVariable final Long conceptID
    ) {
        Concept concept = conceptService.findOne(conceptID);
        return definitionService.findAll(concept);
    }

    @PutMapping("/{definitionID}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateOne(
            @PathVariable final Long conceptID,
            @PathVariable final Long definitionID,
            @RequestBody final DefinitionDTO definitionDTO
    ) {
        Concept concept = conceptService.findOne(conceptID);
        definitionService.updateOne(concept, definitionID, definitionDTO);
    }

    @DeleteMapping("/{definitionID}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeOne(
            @PathVariable final Long conceptID,
            @PathVariable final Long definitionID
    ) {
        Concept concept = conceptService.findOne(conceptID);
        definitionService.removeOne(concept, definitionID);
    }
}
