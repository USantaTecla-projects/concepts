package com.example.backend.api.core.answer.link;

import com.example.backend.api.core.answer.AnswerController;
import com.example.backend.api.core.concept.ConceptController;
import com.example.backend.api.core.answer.model.Answer;
import com.example.backend.api.core.justification.controllers.JustificationController;
import com.example.backend.api.core.justification.models.Justification;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AnswerAssembler implements RepresentationModelAssembler<Answer, EntityModel<Answer>> {
    @Override
    public @NotNull EntityModel<Answer> toModel(@NotNull Answer answer) {
        return EntityModel.of(answer,
                linkTo(methodOn(AnswerController.class).findOne(answer.getId())).withSelfRel(),
                linkTo(methodOn(AnswerController.class).findAll(null)).withRel("answers")
//                linkTo(methodOn(ConceptController.class).findOne(answer.getConcept().getId())).withRel("parent")
//                linkTo(methodOn(JustificationController.class).findOne(answer.getJustification().getId())).withRel("child")
        );
    }

    public List<EntityModel<Answer>> toModelWithPageAsRoot(List<Answer> answerList, int page) {
        return answerList
                .stream()
                .map(answer -> EntityModel.of(
                        answer,
                        linkTo(methodOn(AnswerController.class).findOne(answer.getId())).withSelfRel(),
                        linkTo(methodOn(AnswerController.class).findAll(page)).withRel("answers")))
                .toList();
    }

    public Iterable<Link> generatePageLinks(Page<Answer> answerPage, int page) {
        int firstPage = 0;
        int lastPage = answerPage.getTotalPages() - 1;
        int prevPage = page == firstPage ? firstPage : page - 1;
        int nextPage = page == lastPage ? lastPage : page + 1;

        return List.of(linkTo(methodOn(AnswerController.class).findAll(firstPage)).withRel("first"),
                linkTo(methodOn(AnswerController.class).findAll(prevPage)).withRel("prev"),
                linkTo(methodOn(AnswerController.class).findAll(page)).withSelfRel(),
                linkTo(methodOn(AnswerController.class).findAll(nextPage)).withRel("next"),
                linkTo(methodOn(AnswerController.class).findAll(lastPage)).withRel("last"));
    }
}
