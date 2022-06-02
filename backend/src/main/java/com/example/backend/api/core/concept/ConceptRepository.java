package com.example.backend.api.core.concept;

import com.example.backend.api.core.concept.model.Concept;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ConceptRepository extends CrudRepository<Concept, Long>, PagingAndSortingRepository<Concept,Long> {
}
