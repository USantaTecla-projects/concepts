package com.example.backend.api.core.concept.service;

import com.example.backend.api.core.answer.model.Answer;
import com.example.backend.api.core.answer.AnswerRepository;
import com.example.backend.api.core.concept.IConceptService;
import com.example.backend.api.core.concept.dto.ConceptDTO;
import com.example.backend.api.core.concept.model.Concept;
import com.example.backend.api.core.concept.ConceptRepository;
import com.example.backend.api.exception.model.DTOBadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ConceptService implements IConceptService {

    private final ConceptRepository conceptRepository;
    private final AnswerRepository answerRepository;

    public ConceptService(
            ConceptRepository conceptRepository,
            AnswerRepository answerRepository
    ) {
        this.conceptRepository = conceptRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public Concept create(final ConceptDTO conceptDTO) {
        String text = getTextFromDTO(conceptDTO.getText());
        List<Answer> answers = getAnswersFromDTO(conceptDTO.getAnswers());

        return conceptRepository.save(new Concept(text, answers));
    }

    @Override
    public Concept findOne(Long id) {
        return conceptRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The concept with id = " + id + " has not been found"));
    }

    @Override
    public Page<Concept> findAll(int page){
        int pageSize = 5;
        return conceptRepository.findAll(PageRequest.of(page, pageSize));
    }

    /**
     * Get the text from DTO if exists. If not, throw an Exception.
     *
     * @param text The text in the DTO.
     * @return The text of the DTO.
     */
    private String getTextFromDTO(final String text) {
        return Optional
                .ofNullable(text)
                .filter(t -> !t.isEmpty())
                .orElseThrow(() -> new DTOBadRequestException("Field text in DTO is mandatory"));
    }

    /**
     * Get the List of answers from their identifiers specified in the DTO.
     * If no List with identifiers is provided, then it returns an empty List.
     * If one identifier is not found in the database, it is discarded.
     *
     * @param answers The answers identifiers List provided in the DTO.
     * @return The List with all the answers related with the provided identifiers.
     */
    private List<Answer> getAnswersFromDTO(final List<Long> answers) {
        List<Long> answersIds = Optional
                .ofNullable(answers)
                .orElse(new LinkedList<>());

        return answersIds.stream()
                .map(answerRepository::findAnswerById)
                .filter(Objects::nonNull)
                .toList();
    }

}
