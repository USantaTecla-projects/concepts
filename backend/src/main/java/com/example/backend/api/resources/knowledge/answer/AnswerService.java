package com.example.backend.api.resources.knowledge.answer;

import com.example.backend.api.resources.knowledge.answer.dto.AnswerDTO;
import com.example.backend.api.resources.knowledge.answer.exception.model.AnswerDTOBadRequestException;
import com.example.backend.api.resources.knowledge.answer.exception.model.AnswerNotBelongToConceptException;
import com.example.backend.api.resources.knowledge.answer.exception.model.AnswerNotFoundException;
import com.example.backend.api.resources.knowledge.answer.model.Answer;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    private final ConceptRepository conceptRepository;
    private final AnswerRepository answerRepository;


    public AnswerService(
            final ConceptRepository conceptRepository,
            final AnswerRepository answerRepository
    ){
        this.conceptRepository = conceptRepository;
        this.answerRepository = answerRepository;
    }

    /**
     * Create a new answer by a given DTO (if it is correct) in the given concept.
     *
     * @param concept   The concept where to store the answer.
     * @param answerDTO The data object to create the new answer.
     * @return The created answer.
     * @author Cristian
     */
    public Answer create(
            final Concept concept,
            final AnswerDTO answerDTO
    ) {
        String textFromDTO = answerDTO
                .getTextOptional(answerDTO.getText())
                .orElseThrow(() -> new AnswerDTOBadRequestException("Field text in Answer DTO is mandatory"));

        Boolean isCorrectFromDTO = answerDTO
                .getIsCorrectOptional(answerDTO.getCorrect())
                .orElseThrow(() -> new AnswerDTOBadRequestException("Field isCorrect in Answer DTO is mandatory"));

        Answer answer = answerRepository.save(new Answer(textFromDTO, isCorrectFromDTO, concept.getId(), Collections.emptyList()));
        concept.addAnswer(answer);
        conceptRepository.save(concept);


        return answer;
    }

    /**
     * Find an answer in the database. If the answer does not exist
     * or does not belong to the concept, it throws an exception.
     *
     * @param concept  The concept where the answer should be.
     * @param answerId The answer ID to look for.
     * @return The answer that match the ID and is in the concept.
     * @author Cristian
     */
    public Answer findOne(
            final Concept concept,
            final Long answerId
    ) {
        Answer answer = answerRepository
                .findById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException("The answer with id = " + answerId + " has not been found"));

        if (!concept.containsAnswer(answer))
            throw new AnswerNotBelongToConceptException("The answer with id = " + answerId + " doesn't belong to the concept with id = " + concept.getId());

        return answer;
    }

    /**
     * Find all the answers in the given concept.
     *
     * @param concept The concept where to look for the answers.
     * @return A list of all the answers stored in the concept.
     * @author Cristian
     */
    public List<Answer> findAll(final Concept concept) {
        return Optional
                .ofNullable(concept.getAnswerList())
                .orElseThrow(() -> new AnswerNotFoundException("The concept with id = " + concept.getId() + " has no answers"));
    }

    /**
     * Update the data of the answer by the given ID, with the given DTO, in the given concept.
     *
     * @param concept   The concept where the answer should be.
     * @param answerId  The answer ID to look for.
     * @param answerDTO The data object to update the answer.
     * @author Cristian
     */
    public void updateOne(
            final Concept concept,
            final Long answerId,
            final AnswerDTO answerDTO
    ) {
        Answer answer = findOne(concept, answerId);

        String textFromDTO = answerDTO
                .getTextOptional(answerDTO.getText())
                .orElse(answer.getText());

        Boolean isCorrectFromDTO = answerDTO
                .getIsCorrectOptional(answerDTO.getCorrect())
                .orElse(answer.getCorrect());

        answer.setText(textFromDTO);
        answer.setCorrect(isCorrectFromDTO);

        answerRepository.save(answer);
    }

    /**
     * Remove one answer by the given ID in the given concept.
     *
     * @param concept  The concept where the answer should be.
     * @param answerId The answer ID to look for.
     * @author Cristian
     */
    public void removeOne(
            final Concept concept,
            final Long answerId
    ) {
        Answer answer = findOne(concept, answerId);
        concept.removeAnswer(answer);
        conceptRepository.save(concept);
        answerRepository.delete(answer);

    }


}
