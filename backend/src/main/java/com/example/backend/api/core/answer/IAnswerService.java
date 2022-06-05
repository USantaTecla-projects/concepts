package com.example.backend.api.core.answer;

import com.example.backend.api.core.answer.dto.AnswerDTO;
import com.example.backend.api.core.answer.model.Answer;
import com.example.backend.api.core.concept.model.Concept;
import org.springframework.data.domain.Page;

import java.util.List;

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
    Answer findOne(final Concept concept, final Long id);

    /**
     * Find all Answer by the given page number.
     *
     * @return The list with the corresponding set of results.
     */
    List<Answer> findAll(final Concept concept);

    /**
     * Update an existing Answer, providing the id of the Answer to update and the new info.
     *
     * @param id        The id of the Answer to update.
     * @param answerDTO The new information to update the Answer.
     */
    void updateOne(final Concept concept, final Long id, final AnswerDTO answerDTO);

    /**
     * Delete an Answer by a given id, if not found throws an Exception.
     *
     * @param id The id of the Answer to remove.
     */
    void removeOne(final Concept concept, final Long id);
}
