package com.example.backend.api.core.justification;

import com.example.backend.api.core.answer.IAnswerService;
import com.example.backend.api.core.answer.model.Answer;
import com.example.backend.api.core.concept.IConceptService;
import com.example.backend.api.core.concept.model.Concept;
import com.example.backend.api.core.justification.dto.JustificationReqDTO;
import com.example.backend.api.core.justification.dto.JustificationResDTO;
import com.example.backend.api.core.justification.model.Justification;
import com.example.backend.api.core.justification.util.JustificationAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concepts/{conceptId}/answers/{answerId}/justifications")
public class JustificationController {

    private final JustificationAssembler justificationAssembler;
    private final IConceptService conceptService;
    private final IAnswerService answersService;
    private final IJustificationsService justificationsService;

    public JustificationController(
            JustificationAssembler justificationAssembler,
            IConceptService conceptService,
            IAnswerService answersService,
            IJustificationsService justificationsService) {
        this.justificationAssembler = justificationAssembler;
        this.conceptService = conceptService;
        this.answersService = answersService;
        this.justificationsService = justificationsService;
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public EntityModel<JustificationResDTO> create(
            @PathVariable final Long conceptId,
            @PathVariable final Long answerId,
            @RequestBody final JustificationReqDTO justificationReqDTO
    ) {
        Concept concept = conceptService.findOne(conceptId);
        Answer answer = answersService.findOne(concept, answerId);
        Justification justification = justificationsService.create(concept.getId(), answer, justificationReqDTO);

        return justificationAssembler.toModel(justification);
    }

    @GetMapping("/{justificationId}")
    public EntityModel<JustificationResDTO> findOne(
            @PathVariable final Long conceptId,
            @PathVariable final Long answerId,
            @PathVariable final Long justificationId
    ) {
        Concept concept = conceptService.findOne(conceptId);
        Answer answer = answersService.findOne(concept, answerId);
        Justification justification = justificationsService.findOne(answer, justificationId);

        return justificationAssembler.toModel(justification);
    }

    @GetMapping("/")
    public CollectionModel<EntityModel<JustificationResDTO>> findAll(
            @PathVariable final Long conceptId,
            @PathVariable final Long answerId
    ){
        Concept concept = conceptService.findOne(conceptId);
        Answer answer = answersService.findOne(concept,answerId);
        List<Justification> justificationList = justificationsService.findAll(answer);

        return justificationAssembler.toCollectionModel(justificationList);
    }

    @PutMapping("/{justificationId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateOne(
            @PathVariable final Long conceptId,
            @PathVariable final Long answerId,
            @PathVariable final Long justificationId,
            @RequestBody final JustificationReqDTO justificationReqDTO
    ) {
        Concept concept = conceptService.findOne(conceptId);
        Answer answer = answersService.findOne(concept, answerId);
        justificationsService.updateOne(answer, justificationId, justificationReqDTO);
    }

    @DeleteMapping("/{justificationId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeOne(
            @PathVariable final Long conceptId,
            @PathVariable final Long answerId,
            @PathVariable final Long justificationId
    ) {
        Concept concept = conceptService.findOne(conceptId);
        Answer answer = answersService.findOne(concept, answerId);
        justificationsService.removeOne(answer, justificationId);
    }
}
