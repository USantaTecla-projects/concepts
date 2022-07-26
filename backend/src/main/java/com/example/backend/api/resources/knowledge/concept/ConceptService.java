package com.example.backend.api.resources.knowledge.concept;

import com.example.backend.api.resources.knowledge.concept.dto.ConceptDTO;
import com.example.backend.api.resources.knowledge.concept.exception.model.ConceptDTOBadRequestException;
import com.example.backend.api.resources.knowledge.concept.exception.model.ConceptNotFoundException;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ConceptService {

    private final ConceptRepository conceptRepository;


    public ConceptService(
            ConceptRepository conceptRepository
    ) {
        this.conceptRepository = conceptRepository;
    }

    /**
     * Create a new concept by a given DTO (if it is correct).
     *
     * @param conceptDTO The data object to create the new concept.
     * @return The created concept.
     * @author Cristian
     */
    public Concept create(final ConceptDTO conceptDTO) {
        final String textFromDTO = conceptDTO
                .getTextOptional(conceptDTO.getText())
                .orElseThrow(() -> new ConceptDTOBadRequestException("Field text in Concept DTO is mandatory"));

        return conceptRepository.save(new Concept(textFromDTO, Collections.emptyList()));
    }

    /**
     * Find a concept in the database. If the concept does not exist, it throws an exception.
     *
     * @param conceptId The concept ID to look for.
     * @return The concept that match the ID.
     * @author Cristian
     */
    public Concept findOne(Long conceptId) {
        return conceptRepository.findById(conceptId)
                .orElseThrow(() -> new ConceptNotFoundException("The concept with id = " + conceptId + " has not been found"));
    }

    /**
     * Find a page of concepts in the database by the given page number.
     *
     * @param page The number of the page to look for.
     * @return The page with the concepts.
     * @author Cristian
     */
    public Page<Concept> findAll(int page) {
        int pageSize = 5;
        return conceptRepository.findAll(PageRequest.of(page, pageSize));
    }

    /**
     * Update one concept by the given ID, with the given DTO.
     *
     * @param conceptId  The concept ID to look for.
     * @param conceptDTO The data object to update the concept.
     * @author Cristian
     */
    public void updateOne(Long conceptId, ConceptDTO conceptDTO) {
        Concept concept = findOne(conceptId);
        String textFromDTO = conceptDTO
                .getTextOptional(conceptDTO.getText())
                .orElse(concept.getText());

        concept.setText(textFromDTO);

        conceptRepository.save(concept);
    }

    /**
     * Remove one concept by the given UD.
     *
     * @param conceptId The concept ID to look for.
     * @author Cristian
     */
    public void removeOne(Long conceptId) {
        Concept concept = findOne(conceptId);
        conceptRepository.delete(concept);
    }
}
