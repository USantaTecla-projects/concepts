package com.example.backend.api.core.justification;

import com.example.backend.api.core.concept.model.Concept;
import com.example.backend.api.core.justification.models.Justification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JustificationRepository extends CrudRepository<Justification, Long>, PagingAndSortingRepository<Justification,Long> {
}
