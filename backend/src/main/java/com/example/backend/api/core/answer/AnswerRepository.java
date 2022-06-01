package com.example.backend.api.core.answer;

import com.example.backend.api.core.answer.model.Answer;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer,Long> {
    public Answer findAnswerById(long id);
}
