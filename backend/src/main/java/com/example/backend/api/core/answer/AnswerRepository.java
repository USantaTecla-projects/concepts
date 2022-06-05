package com.example.backend.api.core.answer;

import com.example.backend.api.core.answer.model.Answer;
import com.example.backend.api.core.concept.model.Concept;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AnswerRepository extends CrudRepository<Answer, Long>, PagingAndSortingRepository<Answer, Long> {

}
