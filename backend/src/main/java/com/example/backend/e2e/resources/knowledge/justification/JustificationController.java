package com.example.backend.e2e.resources.knowledge.justification;

import com.example.backend.e2e.resources.knowledge.definition.CrudDefinitionService;
import com.example.backend.e2e.resources.knowledge.definition.model.Definition;
import com.example.backend.e2e.resources.knowledge.concept.CrudConceptService;
import com.example.backend.e2e.resources.knowledge.concept.model.Concept;
import com.example.backend.e2e.resources.knowledge.justification.dto.JustificationDTO;
import com.example.backend.e2e.resources.knowledge.justification.model.Justification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concepts/{conceptID}/definitions/{definitionID}/justifications")
public class JustificationController {

    private final CrudConceptService crudConceptService;
    private final CrudDefinitionService crudDefinitionService;
    private final CrudJustificationService crudJustificationService;

    public JustificationController(
            CrudConceptService crudConceptService,
            CrudDefinitionService crudDefinitionService,
            CrudJustificationService justificationsService) {
        this.crudConceptService = crudConceptService;
        this.crudDefinitionService = crudDefinitionService;
        this.crudJustificationService = justificationsService;
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Justification create(
            @PathVariable final Long conceptID,
            @PathVariable final Long definitionID,
            @RequestBody final JustificationDTO justificationDTO
    ) {
        Concept concept = crudConceptService.findOne(conceptID);
        Definition definition = crudDefinitionService.findOne(concept, definitionID);

        return crudJustificationService.createOne(concept.getId(), definition, justificationDTO);
    }

    @GetMapping("/{justificationID}")
    public Justification findOne(
            @PathVariable final Long conceptID,
            @PathVariable final Long definitionID,
            @PathVariable final Long justificationID
    ) {
        Concept concept = crudConceptService.findOne(conceptID);
        Definition definition = crudDefinitionService.findOne(concept, definitionID);

        return crudJustificationService.findOne(definition, justificationID);
    }

    @GetMapping("/")
    public List<Justification> findAll(
            @PathVariable final Long conceptID,
            @PathVariable final Long definitionID
    ){
        Concept concept = crudConceptService.findOne(conceptID);
        Definition definition = crudDefinitionService.findOne(concept,definitionID);

        return crudJustificationService.findAll(definition);
    }

    @PutMapping("/{justificationID}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateOne(
            @PathVariable final Long conceptID,
            @PathVariable final Long definitionID,
            @PathVariable final Long justificationID,
            @RequestBody final JustificationDTO justificationDTO
    ) {
        Concept concept = crudConceptService.findOne(conceptID);
        Definition definition = crudDefinitionService.findOne(concept, definitionID);
        crudJustificationService.updateOne(definition, justificationID, justificationDTO);
    }

    @DeleteMapping("/{justificationID}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeOne(
            @PathVariable final Long conceptID,
            @PathVariable final Long definitionID,
            @PathVariable final Long justificationID
    ) {
        Concept concept = crudConceptService.findOne(conceptID);
        Definition definition = crudDefinitionService.findOne(concept, definitionID);
        crudJustificationService.removeOne(definition, justificationID);
    }
}
