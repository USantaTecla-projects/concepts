package com.example.backend.api.resources.knowledge.concept;

import com.example.backend.api.resources.knowledge.concept.exception.specific.ConceptDTOBadRequestException;
import com.example.backend.api.resources.knowledge.concept.exception.specific.ConceptNotFoundException;
import com.example.backend.api.resources.knowledge.concept.dto.ConceptDTO;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CrudConceptService {

    private final ConceptRepository conceptRepository;

    public CrudConceptService(final ConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
    }

    public Concept createOne(final ConceptDTO conceptDTO) {
        final String textFromDTO = conceptDTO
                .getTextOptional(conceptDTO.getText())
                .orElseThrow(() -> new ConceptDTOBadRequestException("Field text in Concept DTO is mandatory"));

        return conceptRepository.save(new Concept(textFromDTO, Collections.emptyList()));
    }

    public Concept findOne(Long conceptID) {
        return conceptRepository
                .findById(conceptID)
                .orElseThrow(() -> new ConceptNotFoundException("The concept with ID = " + conceptID + " has not been found"));
    }

    public Page<Concept> findAll(int page) {
        int pageSize = 10;
        return conceptRepository.findAll(PageRequest.of(page, pageSize));
    }

    public void updateOne(Long conceptID, ConceptDTO conceptDTO) {
        Concept concept = findOne(conceptID);
        String textFromDTO = conceptDTO
                .getTextOptional(conceptDTO.getText())
                .orElse(concept.getText());

        concept.setText(textFromDTO);

        conceptRepository.save(concept);
    }

    public void removeOne(Long conceptID) {
        Concept concept = findOne(conceptID);
        conceptRepository.delete(concept);

    }
}
