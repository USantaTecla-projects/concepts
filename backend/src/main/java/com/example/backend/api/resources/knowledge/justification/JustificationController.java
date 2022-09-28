package com.example.backend.api.resources.knowledge.justification;

import com.example.backend.api.resources.knowledge.definition.DefinitionService;
import com.example.backend.api.resources.knowledge.definition.model.Definition;
import com.example.backend.api.resources.knowledge.concept.ConceptService;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import com.example.backend.api.resources.knowledge.justification.dto.JustificationDTO;
import com.example.backend.api.resources.knowledge.justification.model.Justification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concepts/{conceptID}/definitions/{definitionID}/justifications")
public class JustificationController {

    private final ConceptService conceptService;
    private final DefinitionService definitionService;
    private final JustificationService justificationService;

    public JustificationController(
            ConceptService conceptService,
            DefinitionService definitionService,
            JustificationService justificationsService) {
        this.conceptService = conceptService;
        this.definitionService = definitionService;
        this.justificationService = justificationsService;
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Justification create(
            @PathVariable final Long conceptID,
            @PathVariable final Long definitionID,
            @RequestBody final JustificationDTO justificationDTO
    ) {
        Concept concept = conceptService.findOne(conceptID);
        Definition definition = definitionService.findOne(concept, definitionID);

        return justificationService.create(concept.getId(), definition, justificationDTO);
    }

    @GetMapping("/{justificationID}")
    public Justification findOne(
            @PathVariable final Long conceptID,
            @PathVariable final Long definitionID,
            @PathVariable final Long justificationID
    ) {
        Concept concept = conceptService.findOne(conceptID);
        Definition definition = definitionService.findOne(concept, definitionID);

        return justificationService.findOne(definition, justificationID);
    }

    @GetMapping("/")
    public List<Justification> findAll(
            @PathVariable final Long conceptID,
            @PathVariable final Long definitionID
    ){
        Concept concept = conceptService.findOne(conceptID);
        Definition definition = definitionService.findOne(concept,definitionID);

        return justificationService.findAll(definition);
    }

    @PutMapping("/{justificationID}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateOne(
            @PathVariable final Long conceptID,
            @PathVariable final Long definitionID,
            @PathVariable final Long justificationID,
            @RequestBody final JustificationDTO justificationDTO
    ) {
        Concept concept = conceptService.findOne(conceptID);
        Definition definition = definitionService.findOne(concept, definitionID);
        justificationService.updateOne(definition, justificationID, justificationDTO);
    }

    @DeleteMapping("/{justificationID}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeOne(
            @PathVariable final Long conceptID,
            @PathVariable final Long definitionID,
            @PathVariable final Long justificationID
    ) {
        Concept concept = conceptService.findOne(conceptID);
        Definition definition = definitionService.findOne(concept, definitionID);
        justificationService.removeOne(definition, justificationID);
    }
}
