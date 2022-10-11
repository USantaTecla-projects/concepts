package com.example.backend.api.resources.exam.module.question.repository;

import com.example.backend.api.resources.exam.module.question.model.specific.QuestionT1;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface QuestionT1Repository extends CrudRepository<QuestionT1, Long> {

    Optional<QuestionT1> findByConceptIDAndDefinitionID(Long conceptID, Long definitionID);

    boolean existsByConceptIDAndDefinitionID(Long conceptID, Long definitionID);
}
