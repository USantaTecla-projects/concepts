package com.example.backend.api.core.concept;

import com.example.backend.api.core.concept.dto.ConceptReqDTO;
import com.example.backend.api.core.concept.model.Concept;
import org.springframework.data.domain.Page;

public interface IConceptService {

    /**
     * Creates a new concept.
     *
     * @param conceptReqDTO The DTO with the information to create the concept.
     * @return The created Concept, if the DTO is valid.
     */
    Concept create(final ConceptReqDTO conceptReqDTO);

    /**
     * Find one Concept by id.
     *
     * @param id The id of the Concept to find.
     * @return The Concept if found, an Exception if not found.
     */
    Concept findOne(final Long id);

    /**
     * Find all Concepts by the given page number.
     *
     * @param page The page number to look for.
     * @return The page with the corresponding set of results.
     */
    Page<Concept> findAll(int page);

    /**
     * Update an existing Concept, providing the id of the Concept to update and the new info.
     *
     * @param id         The id of the Concept to update.
     * @param conceptReqDTO The new information to update the Concept.
     */
    void updateOne(Long id, ConceptReqDTO conceptReqDTO);

    /**
     * Delete a Concept by a given id, if not found throws an Exception.
     *
     * @param id The id of the Concept to remove.
     */
    void removeOne(Long id);



}
