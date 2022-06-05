package com.example.backend.api.core.answer;

import com.example.backend.api.core.answer.dto.AnswerDTO;
import com.example.backend.api.core.answer.model.Answer;
import com.example.backend.api.core.concept.model.Concept;
import org.springframework.data.domain.Page;

public interface IAnswerService {

    /**
     * Creates a new answer.
     *
     * @param answerDTO The DTO with the information to create the Answer.
     * @return The created Answer, if the DTO is valid.
     */
    Answer create(final Concept concept, final AnswerDTO answerDTO);

    /**
     * Find one Answer by id.
     *
     * @param id The id of the Answer to find.
     * @return The Answer if found, an Exception if not found.
     */
    Answer findOne(final Long id);

    /**
     * Find all Answer by the given page number.
     *
     * @param page The page number to look for.
     * @return The page with the corresponding set of results.
     */
    Page<Answer> findAll(int page);

    /**
     * Update an existing Answer, providing the id of the Answer to update and the new info.
     *
     * @param id        The id of the Answer to update.
     * @param answerDTO The new information to update the Answer.
     */
    void updateOne(Concept concept, Long id, AnswerDTO answerDTO);

    /**
     * Delete an Answer by a given id, if not found throws an Exception.
     *
     * @param id The id of the Answer to remove.
     */
    void removeOne(Concept concept, Long id);
}
