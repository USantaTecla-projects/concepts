package com.example.backend.api.resources.knowledge.answer;

import com.example.backend.api.resources.knowledge.answer.model.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AnswerRepository extends CrudRepository<Answer, Long>, PagingAndSortingRepository<Answer, Long> {
    long countAnswerByIsCorrect(boolean value);
    Page<Answer> findAllByIsCorrect(Pageable pageable,boolean value);

    @Query(value = "SELECT COUNT(*) FROM answer a WHERE NOT a.is_correct", nativeQuery = true)
    long countAvailableType1Questions();

    @Query(value = "SELECT COUNT(*) FROM answer",nativeQuery = true)
    long countAvailableType2Questions();
}
