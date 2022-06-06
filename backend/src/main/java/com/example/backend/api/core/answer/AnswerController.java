package com.example.backend.api.core.answer;

import com.example.backend.api.core.answer.dto.AnswerDTO;
import com.example.backend.api.core.answer.model.Answer;
import com.example.backend.api.core.answer.util.AnswerAssembler;
import com.example.backend.api.core.concept.IConceptService;
import com.example.backend.api.core.concept.model.Concept;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concepts/{conceptId}/answers")
public class AnswerController {

    private final AnswerAssembler answerAssembler;
    private final IConceptService conceptService;
    private final IAnswerService answersService;


    public AnswerController(
            AnswerAssembler answerAssembler,
            IConceptService conceptService,
            IAnswerService answersService
    ) {
        this.answerAssembler = answerAssembler;
        this.conceptService = conceptService;
        this.answersService = answersService;
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public EntityModel<Answer> create(
            @PathVariable final Long conceptId,
            @RequestBody final AnswerDTO answerDTO
    ) {
        Concept concept = conceptService.findOne(conceptId);
        Answer answer = answersService.create(concept, answerDTO);

        return answerAssembler.toModel(answer);
    }

    @GetMapping("/{id}")
    public EntityModel<Answer> findOne(
            @PathVariable final Long conceptId,
            @PathVariable final Long id
    ) {
        Concept concept = conceptService.findOne(conceptId);
        Answer answer = answersService.findOne(concept, id);

        return answerAssembler.toModel(answer);
    }

    @GetMapping("/")
    public CollectionModel<EntityModel<Answer>> findAll(
            @PathVariable final Long conceptId
    ) {
        Concept concept = conceptService.findOne(conceptId);
        List<Answer> answerList = answersService.findAll(concept);

        return answerAssembler.toCollectionModel(answerList);

    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateOne(
            @PathVariable final Long conceptId,
            @PathVariable final Long id,
            @RequestBody final AnswerDTO answerDTO
    ) {
        Concept concept = conceptService.findOne(conceptId);
        answersService.updateOne(concept, id, answerDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeOne(
            @PathVariable final Long conceptId,
            @PathVariable final Long id
    ) {
        Concept concept = conceptService.findOne(conceptId);
        answersService.removeOne(concept, id);
    }
}
