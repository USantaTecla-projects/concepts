package com.example.backend.api.core.concept;

import com.example.backend.api.core.concept.dto.ConceptDTO;
import com.example.backend.api.core.concept.exception.model.ConceptNotFoundException;

import com.example.backend.api.core.concept.model.Concept;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/concepts")
public class ConceptController {
    private final IConceptService conceptsService;


    public ConceptController(IConceptService conceptsService) {
        this.conceptsService = conceptsService;
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public EntityModel<Concept> create(@RequestBody final ConceptDTO conceptDTO) {
        Concept concept = conceptsService.create(conceptDTO);

        return EntityModel.of(concept,
                linkTo(methodOn(ConceptController.class).findOne(concept.getId())).withSelfRel(),
                linkTo(methodOn(ConceptController.class).findAll(null)).withRel("concepts")
        );
    }

    @GetMapping("/{id}")
    public EntityModel<Concept> findOne(@PathVariable final Long id) {
        Concept concept = conceptsService.findOne(id);
        return EntityModel.of(concept,
                linkTo(methodOn(ConceptController.class).findOne(concept.getId())).withSelfRel(),
                linkTo(methodOn(ConceptController.class).findAll(null)).withRel("concepts")
        );
    }

    @GetMapping("/")
    public CollectionModel<EntityModel<Concept>> findAll(@RequestParam Integer page) {
        Page<Concept> conceptPage = conceptsService.findAll(page);
        List<Concept> conceptList = conceptPage.getContent();

        int firstPage = 0;
        int lastPage = conceptPage.getTotalPages() - 1;
        int prevPage = page == firstPage ? firstPage : page - 1;
        int nextPage = page == lastPage ? lastPage : page + 1;

        if (page > lastPage)
            throw new ConceptNotFoundException("The requested page doesn't exists");

        List<EntityModel<Concept>> conceptIterable = conceptList
                .stream()
                .map(concept -> EntityModel.of(
                        concept,
                        linkTo(methodOn(ConceptController.class).findOne(concept.getId())).withSelfRel(),
                        linkTo(methodOn(ConceptController.class).findAll(page)).withRel("concepts")))
                .toList();

        Iterable<Link> linkIterable = List.of(
                linkTo(methodOn(ConceptController.class).findAll(firstPage)).withRel("first"),
                linkTo(methodOn(ConceptController.class).findAll(prevPage)).withRel("prev"),
                linkTo(methodOn(ConceptController.class).findAll(page)).withSelfRel(),
                linkTo(methodOn(ConceptController.class).findAll(nextPage)).withRel("next"),
                linkTo(methodOn(ConceptController.class).findAll(lastPage)).withRel("last")
        );

        return CollectionModel.of(
                conceptIterable,
                linkIterable
        );
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateOne(@PathVariable final Long id, @RequestBody final ConceptDTO conceptDTO) {
        conceptsService.updateOne(id, conceptDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeOne(@PathVariable final Long id) {
        conceptsService.removeOne(id);
    }

}
