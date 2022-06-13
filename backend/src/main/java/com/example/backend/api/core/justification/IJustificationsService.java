package com.example.backend.api.core.justification;

import com.example.backend.api.core.answer.model.Answer;
import com.example.backend.api.core.concept.model.Concept;
import com.example.backend.api.core.justification.dto.JustificationDTO;
import com.example.backend.api.core.justification.model.Justification;

import java.util.List;

public interface IJustificationsService {

    /**
     * Creates a new justification.
     *
     * @param conceptId        The id of the concept where to store the justification to the given answer.
     * @param answer           The answer where to store the justification.
     * @param justificationDTO The DTO with the info to create a new justification.
     * @return The created justification.
     */
    Justification create(final Long conceptId, final Answer answer, final JustificationDTO justificationDTO);

    /**
     * Finds a justification in the given answer.
     *
     * @param answer          The answer where the justification should be.
     * @param justificationId The id of the justification.
     * @return The justification if found.
     */
    Justification findOne(final Answer answer, final Long justificationId);

    /**
     * Find all the answers in the given concept.
     *
     * @param concept The concept where to find the answers.
     * @return The list with the corresponding set of results.
     */
    List<Justification> findAll(final Answer answer);

    /**
     * Updates a justification in the given answer that match with the given id.
     *
     * @param answer           The answer where the justification should be.
     * @param justificationId  The id of the justification.
     * @param justificationDTO The DTO with the new info for the justification.
     */
    void updateOne(final Answer answer, final Long justificationId, JustificationDTO justificationDTO);

    /**
     * Removes a justification from the given answer.
     *
     * @param answer          The answer where the justification should be.
     * @param justificationId The id of the justification.
     */
    void removeOne(final Answer answer, final Long justificationId);


}
