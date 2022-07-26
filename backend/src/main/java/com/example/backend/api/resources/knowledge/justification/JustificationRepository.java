package com.example.backend.api.resources.knowledge.justification;

import com.example.backend.api.resources.knowledge.justification.model.Justification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JustificationRepository extends CrudRepository<Justification, Long>, PagingAndSortingRepository<Justification,Long> {
}
