package com.example.backend.api.resources.knowledge.answer;

import com.example.backend.api.resources.knowledge.answer.model.Answer;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Long>, PagingAndSortingRepository<Answer, Long> {
    long countAnswerByIsCorrect(boolean value);

    Page<Answer> findAllByIsCorrect(Pageable pageable, boolean value);

    @Query(value = "SELECT COUNT(*) FROM answer a WHERE NOT a.is_correct", nativeQuery = true)
    long countAvailableType1Questions();

    @Query(value = "SELECT COUNT(*) FROM answer", nativeQuery = true)
    long countAvailableType2Questions();

    @Query(value = "SELECT * FROM answer a WHERE a.id NOT IN ?1 AND a.is_correct = ?2 LIMIT 1 OFFSET ?3", nativeQuery = true)
    Answer findRandomAnswerBasedOnCorrect(List<Long> answersId, boolean isCorrect, int offset);

    @Query(value = "SELECT * FROM answer a WHERE a.id NOT IN ?1 LIMIT 1 OFFSET ?2", nativeQuery = true)
    Answer findRandomAnswer(List<Long> answersId, int offset);
}
