package com.example.backend.api.core.answer.service;

import com.example.backend.api.core.answer.AnswerRepository;
import com.example.backend.api.core.answer.IAnswerService;
import com.example.backend.api.core.answer.dto.AnswerDTO;
import com.example.backend.api.core.answer.exception.model.AnswerDTOBadRequestException;
import com.example.backend.api.core.answer.exception.model.AnswerNotBelongToConceptException;
import com.example.backend.api.core.answer.exception.model.AnswerNotFoundException;
import com.example.backend.api.core.answer.model.Answer;
import com.example.backend.api.core.concept.ConceptRepository;
import com.example.backend.api.core.concept.model.Concept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService implements IAnswerService {

    private final ConceptRepository conceptRepository;
    private final AnswerRepository answerRepository;

    public AnswerService(
            final ConceptRepository conceptRepository,
            final AnswerRepository answerRepository
    ) {
        this.conceptRepository = conceptRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public Answer create(
            final Concept concept,
            final AnswerDTO answerDTO
    ) {
        String textFromDTO = getTextFromDTO(answerDTO.getText())
                .orElseThrow(() -> new AnswerDTOBadRequestException("Field text in DTO is mandatory"));

        Boolean isCorrectFromDTO = getIsCorrectFromDTO(answerDTO.getIsCorrect())
                .orElseThrow(() -> new AnswerDTOBadRequestException("Field isCorrect in DTO is mandatory"));

        Answer answer = answerRepository.save(new Answer(textFromDTO, isCorrectFromDTO,concept.getId()));

        concept.addAnswer(answer);
        conceptRepository.save(concept);

        return answer;
    }

    @Override
    public Answer findOne(
            final Concept concept,
            final Long id
    ) {

        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new AnswerNotFoundException("The answer with id = " + id + " has not been found"));

        if (!concept.containsAnswer(answer))
            throw new AnswerNotBelongToConceptException(
                    "The answer with id = " + id + " doesn't belong to the concept with id = " + concept.getId()
            );

        return answer;
    }

    @Override
    public List<Answer> findAll(final Concept concept) {
        return concept.getAnswers();
    }

    @Override
    public void updateOne(Concept concept, Long id, AnswerDTO answerDTO) {
        Answer answer = findOne(concept, id);

        String textFromDTO = getTextFromDTO(answerDTO.getText())
                .orElse(answer.getText());

        Boolean isCorrectFromDTO = getIsCorrectFromDTO(answerDTO.getIsCorrect())
                .orElse(answer.getIsCorrect());

        answer.setText(textFromDTO);
        answer.setIsCorrect(isCorrectFromDTO);

        answerRepository.save(answer);
    }

    @Override
    public void removeOne(Concept concept, Long id) {
        Answer answer = findOne(concept, id);
        concept.removeAnswer(answer);

        conceptRepository.save(concept);
        answerRepository.delete(answer);
    }

    /**
     * Get the text from DTO if exists. If not, throw an Exception.
     *
     * @param text The text in the DTO.
     * @return The text of the DTO.
     */
    private Optional<String> getTextFromDTO(final String text) {
        return Optional
                .ofNullable(text)
                .filter(t -> !t.isEmpty());
    }

    /**
     * Get the isCorrect param from DTO if exists. If not, throw an Exception.
     *
     * @param isCorrect Tells if the answer is correct in the DTO.
     * @return The isCorrect param of the DTO.
     */
    private Optional<Boolean> getIsCorrectFromDTO(final Boolean isCorrect) {
        return Optional
                .ofNullable(isCorrect);
    }

}
