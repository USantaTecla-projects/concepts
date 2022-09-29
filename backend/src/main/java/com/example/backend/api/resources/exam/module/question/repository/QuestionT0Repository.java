package com.example.backend.api.resources.exam.module.question.repository;

import com.example.backend.api.resources.exam.module.question.model.specific.QuestionT0;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface QuestionT0Repository extends CrudRepository<QuestionT0, Long> {


    Optional<QuestionT0> findByConceptID(Long conceptID);
}
