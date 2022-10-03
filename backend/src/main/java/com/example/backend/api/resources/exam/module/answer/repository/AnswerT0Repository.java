package com.example.backend.api.resources.exam.module.answer.repository;

import com.example.backend.api.resources.exam.module.answer.model.specific.AnswerT0;
import com.example.backend.api.resources.exam.module.question.model.specific.QuestionT0;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AnswerT0Repository extends CrudRepository<AnswerT0, Long> {
}
