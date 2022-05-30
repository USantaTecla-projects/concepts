package com.example.backend.api.core.repositories;

import com.example.backend.api.core.models.questions.IQuestion;
import com.example.backend.api.core.models.questions.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question,Long> {

    IQuestion findById(long id);
}
