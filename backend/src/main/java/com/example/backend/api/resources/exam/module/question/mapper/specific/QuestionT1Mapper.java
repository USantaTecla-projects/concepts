package com.example.backend.api.resources.exam.module.question.mapper.specific;

import com.example.backend.api.resources.exam.module.question.dto.QuestionDTO;
import com.example.backend.api.resources.exam.module.question.exception.specific.QuestionDTOBadRequestException;
import com.example.backend.api.resources.exam.module.question.mapper.QuestionMapper;
import com.example.backend.api.resources.exam.module.question.model.Question;
import com.example.backend.api.resources.exam.module.question.repository.QuestionT1Repository;

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
