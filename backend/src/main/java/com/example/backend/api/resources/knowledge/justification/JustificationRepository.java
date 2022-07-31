package com.example.backend.api.resources.knowledge.justification;

import com.example.backend.api.resources.knowledge.answer.model.Answer;
import com.example.backend.api.resources.knowledge.justification.model.Justification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface JustificationRepository extends CrudRepository<Justification, Long>, PagingAndSortingRepository<Justification, Long> {
    //    @Query(value = "SELECT * FROM justification j WHERE j.id NOT IN ?1 AND j.answer_id IN (SELECT  id FROM answer a WHERE a.is_correct = false LIMIT 1 OFFSET ?2) LIMIT 1", nativeQuery = true)
    @Query(value = "SELECT * FROM justification j WHERE (j.id NOT IN ?1) AND (j.answer_id IN (SELECT id FROM answer a WHERE NOT a.is_correct)) LIMIT 1 OFFSET ?2", nativeQuery = true)
    Justification findOneJustificationLinkedToIncorrectAnswer(List<Long> justificationsId, int offset);

    @Query(value = "SELECT COUNT(*) FROM answer_justifications j WHERE j.answer_id IN (SELECT a.id FROM answer a WHERE a.is_correct = false)", nativeQuery = true)
    long countAvailableType3Questions();

}
