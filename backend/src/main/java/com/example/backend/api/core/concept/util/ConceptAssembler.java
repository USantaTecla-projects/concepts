package com.example.backend.api.core.concept.util;

import com.example.backend.api.core.answer.util.AnswerAssembler;
import com.example.backend.api.core.concept.ConceptController;
import com.example.backend.api.core.concept.dto.ConceptRestDTO;
import com.example.backend.api.core.concept.model.Concept;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ConceptAssembler implements RepresentationModelAssembler<Concept, EntityModel<ConceptRestDTO>> {

    private final AnswerAssembler answerAssembler;

    public ConceptAssembler(AnswerAssembler answerAssembler) {
        this.answerAssembler = answerAssembler;
    }

    @Override
    public @NotNull EntityModel<ConceptRestDTO> toModel(@NotNull Concept concept) {

        ConceptRestDTO conceptRestDTO = new ConceptRestDTO();
        conceptRestDTO.setId(concept.getId());
        conceptRestDTO.setText(concept.getText());
        conceptRestDTO.setAnswers(
                CollectionModel.of(concept.getAnswers()
                        .stream()
                        .map(answerAssembler::toModel)
                        .toList()
                )
        );

        return EntityModel.of(
                conceptRestDTO,
                linkTo(methodOn(ConceptController.class).findOne(concept.getId())).withSelfRel(),
                linkTo(methodOn(ConceptController.class).findAll(null)).withRel("concepts")
        );
    }

    @Override
    public @NotNull CollectionModel<EntityModel<ConceptRestDTO>> toCollectionModel(@NotNull Iterable<? extends Concept> concepts) {
        return RepresentationModelAssembler.super.toCollectionModel(concepts);
    }

    public @NotNull CollectionModel<EntityModel<ConceptRestDTO>> toCollectionPageModel(List<Concept> concepts, int totalPages, int actualPage) {
        CollectionModel<EntityModel<ConceptRestDTO>> conceptsCollectionModel = toCollectionModel(concepts);

        int firstPage = 0;
        int lastPage = totalPages - 1;
        int prevPage = actualPage == firstPage ? firstPage : actualPage - 1;
        int nextPage = actualPage == lastPage ? lastPage : actualPage + 1;

        conceptsCollectionModel.add(List.of(
                linkTo(methodOn(ConceptController.class).findAll(firstPage)).withRel("first"),
                linkTo(methodOn(ConceptController.class).findAll(prevPage)).withRel("prev"),
                linkTo(methodOn(ConceptController.class).findAll(actualPage)).withSelfRel(),
                linkTo(methodOn(ConceptController.class).findAll(nextPage)).withRel("next"),
                linkTo(methodOn(ConceptController.class).findAll(lastPage)).withRel("last")
        ));

        return conceptsCollectionModel;
    }
}
