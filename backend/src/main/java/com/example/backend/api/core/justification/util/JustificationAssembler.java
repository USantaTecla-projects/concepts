package com.example.backend.api.core.justification.util;

import com.example.backend.api.core.justification.JustificationController;
import com.example.backend.api.core.justification.dto.JustificationResDTO;
import com.example.backend.api.core.justification.model.Justification;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class JustificationAssembler implements RepresentationModelAssembler<Justification, EntityModel<JustificationResDTO>> {
    @Override
    public @NotNull EntityModel<JustificationResDTO> toModel(@NotNull Justification justification) {
        JustificationResDTO justificationResDTO = new JustificationResDTO();
        justificationResDTO.setId(justification.getId());
        justificationResDTO.setText(justification.getText());
        justificationResDTO.setIsCorrect(justification.getCorrect());
        justificationResDTO.setError(justification.getError());

        return EntityModel.of(
                justificationResDTO,
                linkTo(methodOn(JustificationController.class)
                        .findOne(
                                justification.getConceptId(),
                                justification.getAnswerId(),
                                justification.getId()))
                        .withSelfRel(),
                linkTo(methodOn(JustificationController.class)
                        .findAll(
                                justification.getConceptId(),
                                justification.getAnswerId()))
                        .withRel("justifications"));
    }


}
