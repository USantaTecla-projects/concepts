package com.example.backend.api.core.justification.util;

import com.example.backend.api.core.answer.AnswerController;
import com.example.backend.api.core.justification.JustificationController;
import com.example.backend.api.core.justification.model.Justification;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class JustificationAssembler implements RepresentationModelAssembler<Justification, EntityModel<Justification>> {
    @Override
    public @NotNull EntityModel<Justification> toModel(@NotNull Justification justification) {
        return EntityModel.of(
                justification,
                linkTo(methodOn(JustificationController.class)
                        .findOne(
                                justification.getConceptId(),
                                justification.getAnswerId(),
                                justification.getId()))
                        .withSelfRel());
    }


}
