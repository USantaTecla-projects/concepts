package com.example.backend.api.resources.knowledge.concept;

import com.example.backend.api.resources.knowledge.concept.model.Concept;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ConceptRepository extends CrudRepository<Concept, Long>, PagingAndSortingRepository<Concept,Long> {
}