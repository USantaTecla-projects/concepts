package com.example.backend.e2e.resources.exam.domain.family.question.repository;

import com.example.backend.e2e.resources.exam.domain.family.question.model.specific.QuestionT0;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface QuestionT0Repository extends CrudRepository<QuestionT0, Long> {

    Optional<QuestionT0> findByConceptID(Long conceptID);

    boolean existsByConceptID(Long conceptID);
}
