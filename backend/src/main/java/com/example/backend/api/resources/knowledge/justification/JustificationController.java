package com.example.backend.api.resources.knowledge.justification;

import com.example.backend.api.resources.knowledge.answer.AnswerService;
import com.example.backend.api.resources.knowledge.answer.model.Answer;
import com.example.backend.api.resources.knowledge.concept.ConceptService;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import com.example.backend.api.resources.knowledge.justification.dto.JustificationDTO;
import com.example.backend.api.resources.knowledge.justification.model.Justification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concepts/{conceptId}/answers/{answerId}/justifications")
public class JustificationController {

    private final ConceptService conceptService;
    private final AnswerService answersService;
    private final JustificationService justificationService;

    public JustificationController(
            ConceptService conceptService,
            AnswerService answersService,
            JustificationService justificationsService) {
        this.conceptService = conceptService;
        this.answersService = answersService;
        this.justificationService = justificationsService;
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Justification create(
            @PathVariable final Long conceptId,
            @PathVariable final Long answerId,
            @RequestBody final JustificationDTO justificationDTO
    ) {
        Concept concept = conceptService.findOne(conceptId);
        Answer answer = answersService.findOne(concept, answerId);

        return justificationService.create(concept.getId(), answer, justificationDTO);
    }

    @GetMapping("/{justificationId}")
    public Justification findOne(
            @PathVariable final Long conceptId,
            @PathVariable final Long answerId,
            @PathVariable final Long justificationId
    ) {
        Concept concept = conceptService.findOne(conceptId);
        Answer answer = answersService.findOne(concept, answerId);

        return justificationService.findOne(answer, justificationId);
    }

    @GetMapping("/")
    public List<Justification> findAll(
            @PathVariable final Long conceptId,
            @PathVariable final Long answerId
    ){
        Concept concept = conceptService.findOne(conceptId);
        Answer answer = answersService.findOne(concept,answerId);

        return justificationService.findAll(answer);
    }

    @PutMapping("/{justificationId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateOne(
            @PathVariable final Long conceptId,
            @PathVariable final Long answerId,
            @PathVariable final Long justificationId,
            @RequestBody final JustificationDTO justificationDTO
    ) {
        Concept concept = conceptService.findOne(conceptId);
        Answer answer = answersService.findOne(concept, answerId);
        justificationService.updateOne(answer, justificationId, justificationDTO);
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
        justificationService.removeOne(answer, justificationId);
    }
}
