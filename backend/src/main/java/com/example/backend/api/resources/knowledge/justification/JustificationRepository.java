package com.example.backend.api.resources.knowledge.justification;

import com.example.backend.api.resources.knowledge.justification.model.Justification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface JustificationRepository extends CrudRepository<Justification, Long>, PagingAndSortingRepository<Justification, Long> {
    @Query(value = "SELECT * FROM justification j WHERE (j.id NOT IN ?1) AND (j.definition_id IN (SELECT id FROM definition d WHERE NOT d.is_correct)) LIMIT 1 OFFSET ?2", nativeQuery = true)
    Optional<Justification> findOneJustificationLinkedToIncorrectDefinition(List<Long> justificationsID, int offset);

    @Query(value = "SELECT COUNT(*) FROM definition_justification_list j WHERE j.definition_id IN (SELECT d.id FROM definition d WHERE d.is_correct = false)", nativeQuery = true)
    long countAvailableType3Questions();

}
