package com.example.backend.api.resources.exam.domain.family.question.tools.mapper.specific;

import com.example.backend.api.resources.exam.domain.family.question.exception.specific.QuestionDTOBadRequestException;
import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.repository.QuestionT2Repository;
import com.example.backend.api.resources.exam.domain.family.question.tools.mapper.QuestionMapper;

public class QuestionT2Mapper extends QuestionMapper {

    private final QuestionT2Repository questionT2Repository;

    public QuestionT2Mapper(QuestionT2Repository questionT2Repository) {
        this.questionT2Repository = questionT2Repository;
    }


    @Override
    protected Question getQuestionFromDTO() {
        Long conceptID = Long.valueOf((Integer) getTypeDetail("conceptID"));
        Long definitionID = Long.valueOf((Integer) getTypeDetail("definitionID"));

        return questionT2Repository
                .findByConceptIDAndDefinitionID(conceptID,definitionID)
                .orElseThrow(() -> new QuestionDTOBadRequestException("Couldn't find question of type 2 with that conceptID or definitionID"));
    }
}
