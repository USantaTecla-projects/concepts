package com.example.backend.e2e.resources.exam.domain.family.question.tools.mapper.specific;

import com.example.backend.e2e.resources.exam.domain.family.question.model.Question;
import com.example.backend.e2e.resources.exam.domain.family.question.repository.QuestionT0Repository;
import com.example.backend.e2e.resources.exam.domain.family.question.tools.mapper.QuestionMapper;
import com.example.backend.e2e.resources.exam.domain.family.question.exception.specific.QuestionDTOBadRequestException;

public class QuestionT0Mapper extends QuestionMapper {

    private final QuestionT0Repository questionT0Repository;

    public QuestionT0Mapper(QuestionT0Repository questionT0Repository) {
        this.questionT0Repository = questionT0Repository;
    }

    @Override
    protected Question getQuestionFromDTO() {
        Long conceptID = Long.valueOf((Integer) getTypeDetail("conceptID"));

        return questionT0Repository
                .findByConceptID(conceptID)
                .orElseThrow(() -> new QuestionDTOBadRequestException("Couldn't find question of type 0 with that conceptID"));
    }


}
