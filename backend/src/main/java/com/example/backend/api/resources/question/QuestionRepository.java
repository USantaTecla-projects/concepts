package com.example.backend.api.resources.question;

import com.example.backend.api.resources.question.models.QuestionT0;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<QuestionT0,Long> {

    Question findById(long id);
}
