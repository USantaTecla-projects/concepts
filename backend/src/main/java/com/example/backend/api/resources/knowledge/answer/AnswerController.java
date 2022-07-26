package com.example.backend.api.resources.knowledge.answer;

import com.example.backend.api.resources.knowledge.answer.dto.AnswerDTO;
import com.example.backend.api.resources.knowledge.answer.model.Answer;
import com.example.backend.api.resources.knowledge.concept.ConceptService;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concepts/{conceptId}/answers")
public class AnswerController {

    private final ConceptService conceptService;
    private final AnswerService answersService;


    public AnswerController(
            ConceptService conceptService,
            AnswerService answersService
    ) {
        this.conceptService = conceptService;
        this.answersService = answersService;
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Answer create(
            @PathVariable final Long conceptId,
            @RequestBody final AnswerDTO answerDTO
    ) {
        Concept concept = conceptService.findOne(conceptId);
        return answersService.create(concept, answerDTO);

    }

    @GetMapping("/{answerId}")
    public Answer findOne(
            @PathVariable final Long conceptId,
            @PathVariable final Long answerId
    ) {
        Concept concept = conceptService.findOne(conceptId);
        return answersService.findOne(concept, answerId);
    }

    @GetMapping("/")
    public List<Answer> findAll(
            @PathVariable final Long conceptId
    ) {
        Concept concept = conceptService.findOne(conceptId);
        return answersService.findAll(concept);
    }

    @PutMapping("/{answerId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateOne(
            @PathVariable final Long conceptId,
            @PathVariable final Long answerId,
            @RequestBody final AnswerDTO answerDTO
    ) {
        Concept concept = conceptService.findOne(conceptId);
        answersService.updateOne(concept, answerId, answerDTO);
    }

    @DeleteMapping("/{answerId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeOne(
            @PathVariable final Long conceptId,
            @PathVariable final Long answerId
    ) {
        Concept concept = conceptService.findOne(conceptId);
        answersService.removeOne(concept, answerId);
    }
}
