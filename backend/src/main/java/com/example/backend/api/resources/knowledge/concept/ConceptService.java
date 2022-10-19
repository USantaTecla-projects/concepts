package com.example.backend.api.resources.knowledge.concept;

import com.example.backend.api.resources.knowledge.concept.dto.ConceptDTO;
import com.example.backend.api.resources.knowledge.concept.exception.specific.ConceptDTOBadRequestException;
import com.example.backend.api.resources.knowledge.concept.exception.specific.ConceptNotFoundException;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ConceptService {

    private final ConceptRepository conceptRepository;


    public ConceptService(
            final ConceptRepository conceptRepository
    ) {
        this.conceptRepository = conceptRepository;
    }

    public Concept create(final ConceptDTO conceptDTO) {
        final String textFromDTO = conceptDTO
                .getTextOptional(conceptDTO.getText())
                .orElseThrow(() -> new ConceptDTOBadRequestException("Field text in Concept DTO is mandatory"));

        Concept concept = conceptRepository.save(new Concept(textFromDTO, Collections.emptyList()));

        return concept;
    }

    public Concept findOne(Long conceptId) {
        return conceptRepository.findById(conceptId)
                .orElseThrow(() -> new ConceptNotFoundException("The concept with id = " + conceptId + " has not been found"));
    }

    public Page<Concept> findAll(int page) {
        int pageSize = 10;
        return conceptRepository.findAll(PageRequest.of(page, pageSize));
    }

    public void updateOne(Long conceptId, ConceptDTO conceptDTO) {
        Concept concept = findOne(conceptId);
        String textFromDTO = conceptDTO
                .getTextOptional(conceptDTO.getText())
                .orElse(concept.getText());

        concept.setText(textFromDTO);

        conceptRepository.save(concept);
    }

    public void removeOne(Long conceptId) {
        Concept concept = findOne(conceptId);
        conceptRepository.delete(concept);

    }
}
