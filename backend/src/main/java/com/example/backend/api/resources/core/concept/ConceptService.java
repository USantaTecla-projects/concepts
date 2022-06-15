package com.example.backend.api.resources.core.concept;

import com.example.backend.api.resources.core.concept.dto.ConceptDTO;
import com.example.backend.api.resources.core.concept.exception.model.ConceptDTOBadRequestException;
import com.example.backend.api.resources.core.concept.exception.model.ConceptNotFoundException;
import com.example.backend.api.resources.core.concept.model.Concept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ConceptService {

    private final ConceptRepository conceptRepository;


    public ConceptService(
            ConceptRepository conceptRepository
    ) {
        this.conceptRepository = conceptRepository;
    }

    public Concept create(final ConceptDTO conceptDTO) {
        final String textFromDTO = conceptDTO
                .getTextOptional(conceptDTO.getText())
                .orElseThrow(() -> new ConceptDTOBadRequestException("Field text in Concept DTO is mandatory"));

        return conceptRepository.save(new Concept(textFromDTO, Collections.emptyList()));
    }

    public Concept findOne(Long id) {
        return conceptRepository.findById(id)
                .orElseThrow(() -> new ConceptNotFoundException("The concept with id = " + id + " has not been found"));
    }

    public Page<Concept> findAll(int page) {
        int pageSize = 5;
        return conceptRepository.findAll(PageRequest.of(page, pageSize));
    }

    public void updateOne(Long id, ConceptDTO conceptDTO) {
        Concept concept = findOne(id);
        String textFromDTO = conceptDTO
                .getTextOptional(conceptDTO.getText())
                .orElse(concept.getText());

        concept.setText(textFromDTO);

        conceptRepository.save(concept);
    }

    public void removeOne(Long id) {
        Concept concept = findOne(id);
        conceptRepository.delete(concept);
    }
}
