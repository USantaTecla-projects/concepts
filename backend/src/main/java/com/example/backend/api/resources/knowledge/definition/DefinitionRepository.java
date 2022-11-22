package com.example.backend.api.resources.knowledge.definition;

import com.example.backend.api.resources.knowledge.definition.model.Definition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface DefinitionRepository extends CrudRepository<Definition, Long>, PagingAndSortingRepository<Definition, Long> {
    long countDefinitionByIsCorrect(boolean value);

    Page<Definition> findAllByIsCorrect(Pageable pageable, boolean value);

    @Query(value = "SELECT COUNT(*) FROM definition d WHERE NOT d.is_correct", nativeQuery = true)
    long countAvailableType1Questions();

    @Query(value = "SELECT COUNT(*) FROM definition", nativeQuery = true)
    long countAvailableType2Questions();

    @Query(value = "SELECT * FROM definition d WHERE d.id NOT IN ?1 AND d.is_correct = ?2 LIMIT 1 OFFSET ?3", nativeQuery = true)
    Optional<Definition> findRandomDefinitionBasedOnCorrect(List<Long> definitionID, boolean isCorrect, int offset);

    @Query(value = "SELECT * FROM definition d WHERE d.id NOT IN ?1 LIMIT 1 OFFSET ?2", nativeQuery = true)
    Optional<Definition> findRandomDefinition(List<Long> definitionsID, int offset);
}
