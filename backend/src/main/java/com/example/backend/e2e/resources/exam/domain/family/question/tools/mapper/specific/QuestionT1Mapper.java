package com.example.backend.e2e.resources.exam.domain.family.question.tools.mapper.specific;

import com.example.backend.e2e.resources.exam.domain.family.question.exception.specific.QuestionDTOBadRequestException;
import com.example.backend.e2e.resources.exam.domain.family.question.model.Question;
import com.example.backend.e2e.resources.exam.domain.family.question.repository.QuestionT1Repository;
import com.example.backend.e2e.resources.exam.domain.family.question.tools.mapper.QuestionMapper;

public class QuestionT1Mapper extends QuestionMapper {

    private final QuestionT1Repository questionT1Repository;

    public QuestionT1Mapper(QuestionT1Repository questionT1Repository) {
        this.questionT1Repository = questionT1Repository;
    }

    @Override
    protected Question getQuestionFromDTO() {
        Long conceptID = Long.valueOf((Integer) getTypeDetail("conceptID"));
        Long definitionID = Long.valueOf((Integer) getTypeDetail("definitionID"));

        return questionT1Repository
                .findByConceptIDAndDefinitionID(conceptID,definitionID)
                .orElseThrow(() -> new QuestionDTOBadRequestException("Couldn't find question of type 1 with that conceptID or definitionID"));
    }
}
