package com.example.backend.api.resources.knowledge.answer;

import com.example.backend.api.resources.knowledge.answer.model.Answer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AnswerRepository extends CrudRepository<Answer, Long>, PagingAndSortingRepository<Answer, Long> {
}
