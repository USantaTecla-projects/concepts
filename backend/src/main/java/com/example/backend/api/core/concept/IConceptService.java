package com.example.backend.api.core.concept;

import com.example.backend.api.core.concept.dto.ConceptDTO;
import com.example.backend.api.core.concept.model.Concept;

public interface IConceptService {

    Concept create(final ConceptDTO conceptDTO);

    Concept findOne(final Long id);

}
