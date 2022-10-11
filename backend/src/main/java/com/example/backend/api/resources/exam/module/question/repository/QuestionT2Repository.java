package com.example.backend.api.resources.exam.module.question.repository;

import com.example.backend.api.resources.exam.module.question.model.specific.QuestionT2;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface QuestionT2Repository extends CrudRepository<QuestionT2, Long> {

    Optional<QuestionT2> findByConceptIDAndDefinitionID(Long conceptID, Long definitionID);

    boolean existsByConceptIDAndDefinitionID(Long conceptID, Long definitionID);
}
