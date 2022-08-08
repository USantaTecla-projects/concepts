package com.example.backend.api.resources.knowledge.concept;

import com.example.backend.api.resources.knowledge.concept.model.Concept;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ConceptRepository extends CrudRepository<Concept, Long>, PagingAndSortingRepository<Concept, Long> {

    @Query(value = "SELECT COUNT(*) FROM concept", nativeQuery = true)
    long countAvailableType0Questions();

    @Query(value = "SELECT * FROM concept c WHERE c.id NOT IN ?1 LIMIT 1 OFFSET ?2", nativeQuery = true)
    Optional<Concept> findRandomConcept(List<Long> conceptsId, int offset);
}
