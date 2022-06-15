package com.example.backend.api.resources.core.justification;

import com.example.backend.api.resources.core.justification.model.Justification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JustificationRepository extends CrudRepository<Justification, Long>, PagingAndSortingRepository<Justification,Long> {
}
