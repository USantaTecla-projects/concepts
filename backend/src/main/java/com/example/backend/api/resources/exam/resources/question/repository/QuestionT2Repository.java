package com.example.backend.api.resources.exam.resources.question.repository;

import com.example.backend.api.resources.exam.resources.question.model.specific.QuestionT1;
import com.example.backend.api.resources.exam.resources.question.model.specific.QuestionT2;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface QuestionT2Repository extends CrudRepository<QuestionT2, Long> {

    Optional<QuestionT2> findByConceptIDAndDefinitionID(Long conceptID, Long definitionID);

}
