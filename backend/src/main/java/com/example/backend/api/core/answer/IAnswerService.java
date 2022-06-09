package com.example.backend.api.core.answer;

import com.example.backend.api.core.answer.dto.AnswerDTO;
import com.example.backend.api.core.answer.model.Answer;
import com.example.backend.api.core.concept.model.Concept;

import java.util.List;

public interface IAnswerService {

    /**
     * Creates a new answer.
     *
     * @param concept   The concept where to store the answer.
     * @param answerDTO The DTO with the information to create the answer.
     * @return The created answer, if the DTO is valid.
     */
    Answer create(final Concept concept, final AnswerDTO answerDTO);

    /**
     * Find one answer by id in the given concept.
     *
     * @param concept The concept where to find the answer.
     * @param id      The id of the Answer to find.
     * @return The Answer if found, an Exception if not found.
     */
    Answer findOne(final Concept concept, final Long id);

    /**
     * Find all the answers in the given concept.
     *
     * @param concept The concept where to find the answers.
     * @return The list with the corresponding set of results.
     */
    List<Answer> findAll(final Concept concept);

    /**
     * Update an existing answer in the given concept, providing the id of the Answer to update and the new info.
     *
     * @param concept   The concept where to find the answer.
     * @param id        The id of the answer to update.
     * @param answerDTO The new information to update the answer.
     */
    void updateOne(final Concept concept, final Long id, final AnswerDTO answerDTO);

    /**
     * Delete an answer by a given id in the given concept, if not found throws an exception.
     *
     * @param concept The concept where to find the answer.
     * @param id      The id of the answer to remove.
     */
    void removeOne(final Concept concept, final Long id);
}
