package com.example.backend.api.resources.core.answer;

import com.example.backend.api.resources.core.answer.dto.AnswerDTO;
import com.example.backend.api.resources.core.answer.model.Answer;
import com.example.backend.api.resources.core.concept.ConceptService;
import com.example.backend.api.resources.core.concept.model.Concept;
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

    @GetMapping("/{id}")
    public Answer findOne(
            @PathVariable final Long conceptId,
            @PathVariable final Long id
    ) {
        Concept concept = conceptService.findOne(conceptId);
        return answersService.findOne(concept, id);
    }

    @GetMapping("/")
    public List<Answer> findAll(
            @PathVariable final Long conceptId
    ) {
        Concept concept = conceptService.findOne(conceptId);
        return answersService.findAll(concept);
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
