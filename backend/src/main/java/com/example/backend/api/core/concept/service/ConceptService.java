package com.example.backend.api.core.concept.service;

import com.example.backend.api.core.concept.ConceptRepository;
import com.example.backend.api.core.concept.IConceptService;
import com.example.backend.api.core.concept.dto.ConceptDTO;
import com.example.backend.api.core.concept.exception.model.ConceptDTOBadRequestException;
import com.example.backend.api.core.concept.exception.model.ConceptNotFoundException;
import com.example.backend.api.core.concept.model.Concept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class ConceptService implements IConceptService {

    private final ConceptRepository conceptRepository;


    public ConceptService(
            ConceptRepository conceptRepository
    ) {
        this.conceptRepository = conceptRepository;
    }

    @Override
    public Concept create(final ConceptDTO conceptDTO) {
        String textFromDTO = getTextFromDTO(conceptDTO.getText())
                .orElseThrow(() -> new ConceptDTOBadRequestException("Field text in DTO is mandatory"));

        return conceptRepository.save(new Concept(textFromDTO, Collections.emptyList()));
    }

    @Override
    public Concept findOne(Long id) {
        return conceptRepository.findById(id)
                .orElseThrow(() -> new ConceptNotFoundException("The concept with id = " + id + " has not been found"));
    }

    @Override
    public Page<Concept> findAll(int page) {
        int pageSize = 5;
        return conceptRepository.findAll(PageRequest.of(page, pageSize));
    }

    @Override
    public void updateOne(Long id, ConceptDTO conceptDTO) {
        Concept concept = findOne(id);
        String textFromDTO = getTextFromDTO(conceptDTO.getText())
                .orElse(concept.getText());

        concept.setText(textFromDTO);

        conceptRepository.save(concept);
    }

    @Override
    public void removeOne(Long id) {
        Concept concept = findOne(id);
        conceptRepository.delete(concept);
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

}
