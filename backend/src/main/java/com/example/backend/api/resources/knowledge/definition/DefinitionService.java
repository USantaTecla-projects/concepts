package com.example.backend.api.resources.knowledge.definition;

import com.example.backend.api.resources.knowledge.definition.dto.DefinitionDTO;
import com.example.backend.api.resources.knowledge.definition.exception.model.DefinitionDTOBadRequestException;
import com.example.backend.api.resources.knowledge.definition.exception.model.DefinitionNotBelongToConceptException;
import com.example.backend.api.resources.knowledge.definition.exception.model.DefinitionNotFoundException;
import com.example.backend.api.resources.knowledge.definition.model.Definition;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DefinitionService {

    private final ConceptRepository conceptRepository;
    private final DefinitionRepository definitionRepository;


    public DefinitionService(
            final ConceptRepository conceptRepository,
            final DefinitionRepository definitionRepository
    ){
        this.conceptRepository = conceptRepository;
        this.definitionRepository = definitionRepository;
    }

    /**
     * Create a new definition by a given DTO (if it is correct) in the given concept.
     *
     * @param concept   The concept where to store the definition.
     * @param definitionDTO The data object to create the new definition.
     * @return The created definition.
     * @author Cristian
     */
    public Definition create(
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

    /**
     * Find a definition in the database. If the definition does not exist
     * or does not belong to the concept, it throws an exception.
     *
     * @param concept  The concept where the definition should be.
     * @param definitionID The definition ID to look for.
     * @return The definition that match the ID and is in the concept.
     * @author Cristian
     */
    public Definition findOne(
            final Concept concept,
            final Long definitionID
    ) {
        Definition definition = definitionRepository
                .findById(definitionID)
                .orElseThrow(() -> new DefinitionNotFoundException("The definition with id = " + definitionID + " has not been found"));

        if (!concept.containsDefinition(definition))
            throw new DefinitionNotBelongToConceptException("The definition with id = " + definitionID + " doesn't belong to the concept with id = " + concept.getId());

        return definition;
    }

    /**
     * Find all the definitions in the given concept.
     *
     * @param concept The concept where to look for the definitions.
     * @return A list of all the definitions stored in the concept.
     * @author Cristian
     */
    public List<Definition> findAll(final Concept concept) {
        return Optional
                .ofNullable(concept.getDefinitionList())
                .orElseThrow(() -> new DefinitionNotFoundException("The concept with id = " + concept.getId() + " has no definitions"));
    }

    /**
     * Update the data of the definition by the given ID, with the given DTO, in the given concept.
     *
     * @param concept   The concept where the definition should be.
     * @param definitionID  The definition ID to look for.
     * @param definitionDTO The data object to update the definition.
     * @author Cristian
     */
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

    /**
     * Remove one definition by the given ID in the given concept.
     *
     * @param concept  The concept where the definition should be.
     * @param definitionID The definition ID to look for.
     * @author Cristian
     */
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
