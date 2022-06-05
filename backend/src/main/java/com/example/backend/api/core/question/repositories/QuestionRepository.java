package com.example.backend.api.core.question.repositories;

import com.example.backend.api.core.question.models.Question;
import com.example.backend.api.core.question.models.IQuestion;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question,Long> {

    IQuestion findById(long id);
}
