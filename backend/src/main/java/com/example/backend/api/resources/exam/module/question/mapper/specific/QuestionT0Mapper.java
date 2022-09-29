package com.example.backend.api.resources.exam.module.question.mapper.specific;

import com.example.backend.api.resources.exam.module.question.dto.QuestionDTO;
import com.example.backend.api.resources.exam.module.question.exception.specific.QuestionDTOBadRequestException;
import com.example.backend.api.resources.exam.module.question.mapper.QuestionMapper;
import com.example.backend.api.resources.exam.module.question.model.Question;
import com.example.backend.api.resources.exam.module.question.repository.QuestionT0Repository;

public class QuestionT0Mapper extends QuestionMapper {

    private final QuestionT0Repository questionT0Repository;

    public QuestionT0Mapper(QuestionT0Repository questionT0Repository) {
        this.questionT0Repository = questionT0Repository;
    }

    @Override
    protected Question getQuestionFromDTO() {
        Long conceptID = Long.valueOf((Integer) getTypeDetail("conceptId"));

        return questionT0Repository
                .findByConceptID(conceptID)
                .orElseThrow(() -> new QuestionDTOBadRequestException("Couldn't find question of type 0 with that conceptID"));
    }


}
