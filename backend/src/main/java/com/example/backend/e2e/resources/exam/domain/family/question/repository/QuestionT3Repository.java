package com.example.backend.e2e.resources.exam.domain.family.question.repository;

import com.example.backend.e2e.resources.exam.domain.family.question.model.specific.QuestionT3;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface QuestionT3Repository extends CrudRepository<QuestionT3, Long> {

    Optional<QuestionT3> findByConceptIDAndDefinitionIDAndJustificationID(Long conceptID, Long definitionID, Long justificationID);

    boolean existsByConceptIDAndDefinitionIDAndJustificationID(Long conceptID, Long definitionID, Long justificationID);
}
