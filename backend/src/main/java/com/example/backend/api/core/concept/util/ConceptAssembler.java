package com.example.backend.api.core.concept.util;

import com.example.backend.api.core.concept.ConceptController;
import com.example.backend.api.core.concept.model.Concept;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ConceptAssembler implements RepresentationModelAssembler<Concept, EntityModel<Concept>> {

    @Override
    public @NotNull EntityModel<Concept> toModel(@NotNull Concept concept) {
        return EntityModel.of(concept,
                linkTo(methodOn(ConceptController.class).findOne(concept.getId())).withSelfRel(),
                linkTo(methodOn(ConceptController.class).findAll(null)).withRel("concepts")
        );
    }

    public List<EntityModel<Concept>> toModelWithPageAsRoot(List<Concept> conceptList, int page) {
        return conceptList
                .stream()
                .map(concept -> EntityModel.of(
                        concept,
                        linkTo(methodOn(ConceptController.class).findOne(concept.getId())).withSelfRel(),
                        linkTo(methodOn(ConceptController.class).findAll(page)).withRel("concepts")))
                .toList();
    }

    public Iterable<Link> generatePageLinks(Page<Concept> conceptPage, int page) {
        int firstPage = 0;
        int lastPage = conceptPage.getTotalPages() - 1;
        int prevPage = page == firstPage ? firstPage : page - 1;
        int nextPage = page == lastPage ? lastPage : page + 1;

        return List.of(linkTo(methodOn(ConceptController.class).findAll(firstPage)).withRel("first"),
                linkTo(methodOn(ConceptController.class).findAll(prevPage)).withRel("prev"),
                linkTo(methodOn(ConceptController.class).findAll(page)).withSelfRel(),
                linkTo(methodOn(ConceptController.class).findAll(nextPage)).withRel("next"),
                linkTo(methodOn(ConceptController.class).findAll(lastPage)).withRel("last"));
    }

}
