package com.example.backend.api.core.concept;

import com.example.backend.api.core.concept.model.Concept;
import org.springframework.data.repository.CrudRepository;

public interface ConceptRepository extends CrudRepository<Concept, Long> {
}
