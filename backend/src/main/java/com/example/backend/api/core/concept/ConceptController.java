package com.example.backend.api.core.concept;

import com.example.backend.api.core.concept.dto.ConceptDTO;
import com.example.backend.api.core.concept.model.Concept;
import com.example.backend.api.core.concept.util.ConceptAssembler;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/concepts")
public class ConceptController {
    private final IConceptService conceptsService;
    private final ConceptAssembler conceptAssembler;


    public ConceptController(IConceptService conceptsService, ConceptAssembler conceptAssembler) {
        this.conceptsService = conceptsService;
        this.conceptAssembler = conceptAssembler;
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public EntityModel<Concept> create(@RequestBody final ConceptDTO conceptDTO) {
        return conceptAssembler.toModel(conceptsService.create(conceptDTO));
    }

    @GetMapping("/{id}")
    public EntityModel<Concept> findOne(@PathVariable final Long id) {
        Concept concept = conceptsService.findOne(id);
        return conceptAssembler.toModel(concept);
    }

    @GetMapping("/")
    public CollectionModel<EntityModel<Concept>> findAll(@RequestParam Integer page) {
        Page<Concept> conceptPage = conceptsService.findAll(page);
        List<Concept> conceptList = conceptPage.getContent();

        int lastPage = conceptPage.getTotalPages() - 1;

        if (page > lastPage)
            throw new EntityNotFoundException("The requested page doesn't exists");

        List<EntityModel<Concept>> conceptIterable = conceptAssembler.toModelWithPageAsRoot(conceptList,page);
        Iterable<Link> linkIterable =  conceptAssembler.generatePageLinks(conceptPage, page);

        return CollectionModel.of(
                conceptIterable,
                linkIterable
        );
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateOne(@PathVariable final Long id,@RequestBody final ConceptDTO conceptDTO){
        conceptsService.updateOne(id,conceptDTO);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeOne(@PathVariable final Long id){
        conceptsService.removeOne(id);
    }



}
