package com.example.backend.api.resources.knowledge.concept;

import com.example.backend.api.resources.knowledge.concept.model.Concept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ConceptRepository extends CrudRepository<Concept, Long>, PagingAndSortingRepository<Concept, Long> {

    @Query(value = "SELECT COUNT(*) FROM concept", nativeQuery = true)
    long countAvailableType0Questions();
}
