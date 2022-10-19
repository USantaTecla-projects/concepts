package com.example.backend.api.resources.exam.domain.family.question.tools.mapper.specific;

import com.example.backend.api.resources.exam.domain.family.question.exception.specific.QuestionDTOBadRequestException;
import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.tools.mapper.QuestionMapper;
import com.example.backend.api.resources.exam.domain.family.question.repository.QuestionT3Repository;

public class QuestionT3Mapper extends QuestionMapper {

    private final QuestionT3Repository questionT3Repository;

    public QuestionT3Mapper(QuestionT3Repository questionT3Repository) {
        this.questionT3Repository = questionT3Repository;
    }

    @Override
    protected Question getQuestionFromDTO() {
        Long conceptID = Long.valueOf((Integer) getTypeDetail("conceptID"));
        Long definitionID = Long.valueOf((Integer) getTypeDetail("definitionID"));
        Long justificationID = Long.valueOf((Integer) getTypeDetail("justificationID"));

        return questionT3Repository
                .findByConceptIDAndDefinitionIDAndJustificationID(conceptID,definitionID,justificationID)
                .orElseThrow(() -> new QuestionDTOBadRequestException("Couldn't find question of type 3 with that conceptID or definitionID or justificationID"));
    }
}
