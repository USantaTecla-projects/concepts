package com.example.backend.api.resources.knowledge.definition;

import com.example.backend.api.resources.knowledge.definition.dto.DefinitionDTO;
import com.example.backend.api.resources.knowledge.definition.exception.specific.DefinitionDTOBadRequestException;
import com.example.backend.api.resources.knowledge.definition.model.Definition;
import com.example.backend.api.resources.knowledge.definition.exception.specific.DefinitionNotBelongToConceptException;
import com.example.backend.api.resources.knowledge.definition.exception.specific.DefinitionNotFoundException;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CrudDefinitionService {

    private final ConceptRepository conceptRepository;

    private final DefinitionRepository definitionRepository;

    public CrudDefinitionService(
            final ConceptRepository conceptRepository,
            final DefinitionRepository definitionRepository
    ) {
        this.conceptRepository = conceptRepository;
        this.definitionRepository = definitionRepository;
    }

    public Definition createOne(
            final Concept concept,
            final DefinitionDTO definitionDTO
    ) {
        String textFromDTO = definitionDTO
                .getTextOptional(definitionDTO.getText())
                .orElseThrow(() -> new DefinitionDTOBadRequestException("Field text in definition DTO is mandatory"));

        Boolean isCorrectFromDTO = definitionDTO
                .getIsCorrectOptional(definitionDTO.getCorrect())
                .orElseThrow(() -> new DefinitionDTOBadRequestException("Field isCorrect in definition DTO is mandatory"));

        Definition definition = definitionRepository.save(new Definition(textFromDTO, isCorrectFromDTO, concept.getId(), Collections.emptyList()));
        concept.addDefinition(definition);
        conceptRepository.save(concept);


        return definition;
    }

    public Definition findOne(
            final Concept concept,
            final Long definitionID
    ) {
        Definition definition = definitionRepository
                .findById(definitionID)
                .orElseThrow(() -> new DefinitionNotFoundException("The definition with id = " + definitionID + " has not been found"));

        if (!concept.containsDefinition(definition)) {
            throw new DefinitionNotBelongToConceptException("The definition with id = "
                    + definitionID
                    + " doesn't belong to the concept with id = " + concept.getId());
        }

        return definition;
    }
    
    public List<Definition> findAll(final Concept concept) {
        return Optional
                .ofNullable(concept.getDefinitionList())
                .orElseThrow(() -> new DefinitionNotFoundException("The concept with id = " + concept.getId() + " has no definitions"));
    }
    
    public void updateOne(
            final Concept concept,
            final Long definitionID,
            final DefinitionDTO definitionDTO
    ) {
        Definition definition = findOne(concept, definitionID);

        String textFromDTO = definitionDTO
                .getTextOptional(definitionDTO.getText())
                .orElse(definition.getText());

        Boolean isCorrectFromDTO = definitionDTO
                .getIsCorrectOptional(definitionDTO.getCorrect())
                .orElse(definition.getCorrect());

        definition.setText(textFromDTO);
        definition.setCorrect(isCorrectFromDTO);

        definitionRepository.save(definition);
    }
    
    public void removeOne(
            final Concept concept,
            final Long definitionID
    ) {
        Definition definition = findOne(concept, definitionID);
        concept.removeDefinition(definition);
        conceptRepository.save(concept);
        definitionRepository.delete(definition);

    }
}
