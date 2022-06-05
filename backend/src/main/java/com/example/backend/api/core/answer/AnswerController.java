package com.example.backend.api.core.answer;

import com.example.backend.api.core.answer.dto.AnswerDTO;
import com.example.backend.api.core.answer.model.Answer;
import com.example.backend.api.core.answer.link.AnswerAssembler;
import com.example.backend.api.core.concept.IConceptService;
import com.example.backend.api.core.concept.model.Concept;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/concepts/{conceptId}/answers")
public class AnswerController {

    private final IConceptService conceptService;
    private final IAnswerService answersService;

    private final AnswerAssembler answerAssembler;

    public AnswerController(IConceptService conceptService, IAnswerService answersService, AnswerAssembler answerAssembler) {
        this.conceptService = conceptService;
        this.answersService = answersService;
        this.answerAssembler = answerAssembler;
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public EntityModel<Answer> create(@PathVariable final Long conceptId, @RequestBody final AnswerDTO answerDTO) {
        Concept concept = conceptService.findOne(conceptId);
        return answerAssembler.toModel(answersService.create(concept, answerDTO));
    }

    @GetMapping("/{id}")
    public EntityModel<Answer> findOne(@PathVariable final Long id) {
        Answer answer = answersService.findOne(id);
        return answerAssembler.toModel(answer);
    }

    @GetMapping("/")
    public CollectionModel<EntityModel<Answer>> findAll(@RequestParam Integer page) {
        Page<Answer> answerPage = answersService.findAll(page);
        List<Answer> answerList = answerPage.getContent();

        int lastPage = answerPage.getTotalPages() - 1;

        if (page > lastPage)
            throw new EntityNotFoundException("The requested page doesn't exists");

        List<EntityModel<Answer>> answerIterable = answerAssembler.toModelWithPageAsRoot(answerList, page);
        Iterable<Link> linkIterable = answerAssembler.generatePageLinks(answerPage, page);

        return CollectionModel.of(
                answerIterable,
                linkIterable
        );
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
    public void removeOne(@PathVariable final Long conceptId, @PathVariable final Long id) {
        Concept concept = conceptService.findOne(conceptId);
        answersService.removeOne(concept,id);
    }
}
