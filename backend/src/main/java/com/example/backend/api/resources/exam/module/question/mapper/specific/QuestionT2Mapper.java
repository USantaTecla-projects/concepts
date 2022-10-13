package com.example.backend.api.resources.exam.module.question.mapper.specific;

import com.example.backend.api.resources.exam.module.question.dto.QuestionDTO;
import com.example.backend.api.resources.exam.module.question.exception.specific.QuestionDTOBadRequestException;
import com.example.backend.api.resources.exam.module.question.mapper.QuestionMapper;
import com.example.backend.api.resources.exam.module.question.model.Question;
import com.example.backend.api.resources.exam.module.question.repository.QuestionT2Repository;

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
