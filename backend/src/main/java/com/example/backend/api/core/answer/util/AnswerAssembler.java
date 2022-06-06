package com.example.backend.api.core.answer.util;

import com.example.backend.api.core.answer.AnswerController;
import com.example.backend.api.core.answer.model.Answer;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AnswerAssembler implements RepresentationModelAssembler<Answer, EntityModel<Answer>> {
    @Override
    public @NotNull EntityModel<Answer> toModel(@NotNull Answer answer) {
        return EntityModel.of(
                answer,
                linkTo(methodOn(AnswerController.class).findOne(answer.getConceptId(),answer.getId())).withSelfRel(),
                linkTo(methodOn(AnswerController.class).findAll(answer.getConceptId())).withRel("answers")
        );
    }
}
