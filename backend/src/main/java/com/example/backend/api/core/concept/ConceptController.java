package com.example.backend.api.core.concept;

import com.example.backend.api.core.concept.dto.ConceptReqDTO;
import com.example.backend.api.core.concept.dto.ConceptResDTO;
import com.example.backend.api.core.concept.exception.model.ConceptNotFoundException;
import com.example.backend.api.core.concept.model.Concept;
import com.example.backend.api.core.concept.util.ConceptAssembler;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concepts")
public class ConceptController {

    private final ConceptAssembler conceptAssembler;
    private final IConceptService conceptsService;


    public ConceptController(ConceptAssembler conceptAssembler, IConceptService conceptsService) {
        this.conceptAssembler = conceptAssembler;
        this.conceptsService = conceptsService;
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public EntityModel<ConceptResDTO> create(@RequestBody final ConceptReqDTO conceptReqDTO) {
        Concept concept = conceptsService.create(conceptReqDTO);
        return conceptAssembler.toModel(concept);
    }

    @GetMapping("/{id}")
    public EntityModel<ConceptResDTO> findOne(@PathVariable final Long id) {
        Concept concept = conceptsService.findOne(id);
        return conceptAssembler.toModel(concept);
    }

    @GetMapping("/")
    public CollectionModel<EntityModel<ConceptResDTO>> findAll(@RequestParam Integer page) {
        Page<Concept> conceptPage = conceptsService.findAll(page);
        List<Concept> conceptList = conceptPage.getContent();

        int lastPage = conceptPage.getTotalPages() - 1;
        int totalPages = conceptPage.getTotalPages();

        if (page > lastPage)
            throw new ConceptNotFoundException("The requested page doesn't exists");

        return conceptAssembler.toCollectionPageModel(conceptList,totalPages,page);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateOne(@PathVariable final Long id, @RequestBody final ConceptReqDTO conceptReqDTO) {
        conceptsService.updateOne(id, conceptReqDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeOne(@PathVariable final Long id) {
        conceptsService.removeOne(id);
    }

}
