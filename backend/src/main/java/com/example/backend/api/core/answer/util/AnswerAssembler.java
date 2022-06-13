package com.example.backend.api.core.answer.util;

import com.example.backend.api.core.answer.AnswerController;
import com.example.backend.api.core.answer.dto.AnswerResDTO;
import com.example.backend.api.core.answer.model.Answer;
import com.example.backend.api.core.justification.util.JustificationAssembler;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AnswerAssembler implements RepresentationModelAssembler<Answer, EntityModel<AnswerResDTO>> {
    private final JustificationAssembler justificationAssembler;

    public AnswerAssembler(JustificationAssembler justificationAssembler) {
        this.justificationAssembler = justificationAssembler;
    }

    @Override
    public @NotNull EntityModel<AnswerResDTO> toModel(@NotNull Answer answer) {

        AnswerResDTO answerResDTO = new AnswerResDTO();
        answerResDTO.setId(answer.getId());
        answerResDTO.setText(answer.getText());
        answerResDTO.setIsCorrect(answer.getCorrect());
        answerResDTO.setJustifications(
                CollectionModel.of(answer.getJustifications()
                        .stream()
                        .map(justificationAssembler::toModel)
                        .toList())
        );

        return EntityModel.of(
                answerResDTO,
                linkTo(methodOn(AnswerController.class).findOne(answer.getConceptId(), answer.getId())).withSelfRel(),
                linkTo(methodOn(AnswerController.class).findAll(answer.getConceptId())).withRel("answers")
        );
    }

    @Override
    public @NotNull CollectionModel<EntityModel<AnswerResDTO>> toCollectionModel(@NotNull Iterable<? extends Answer> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
