package com.example.backend.api.resources.exam.resources.question.repository;

import com.example.backend.api.resources.exam.resources.question.model.specific.QuestionT2;
import com.example.backend.api.resources.exam.resources.question.model.specific.QuestionT3;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface QuestionT3Repository extends CrudRepository<QuestionT3, Long> {

    Optional<QuestionT3> findByConceptIDAndDefinitionIDAndJustificationID(Long conceptID, Long definitionID, Long justificationID);

}
