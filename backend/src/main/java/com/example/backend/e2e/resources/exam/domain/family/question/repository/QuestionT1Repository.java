package com.example.backend.e2e.resources.exam.domain.family.question.repository;

import com.example.backend.e2e.resources.exam.domain.family.question.model.specific.QuestionT1;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface QuestionT1Repository extends CrudRepository<QuestionT1, Long> {

    Optional<QuestionT1> findByConceptIDAndDefinitionID(Long conceptID, Long definitionID);

    boolean existsByConceptIDAndDefinitionID(Long conceptID, Long definitionID);
}
