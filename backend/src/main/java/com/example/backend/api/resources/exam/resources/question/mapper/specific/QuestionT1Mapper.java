package com.example.backend.api.resources.exam.resources.question.mapper.specific;

import com.example.backend.api.resources.exam.dto.QuestionDTO;
import com.example.backend.api.resources.exam.resources.question.mapper.QuestionMapper;
import com.example.backend.api.resources.exam.resources.question.model.Question;
import com.example.backend.api.resources.exam.resources.question.model.specific.QuestionT1;

public class QuestionT1Mapper extends QuestionMapper {

    @Override
    protected Question createQuestionFromDTO(QuestionDTO questionDTO) {
        Long conceptID = Long.valueOf((Integer) getTypeDetail("conceptId"));
        Long definitionID = Long.valueOf((Integer) getTypeDetail("definitionId"));

        QuestionT1 questionT1 = new QuestionT1();
        questionT1.setConceptID(conceptID);
        questionT1.setDefinitionID(definitionID);

        return questionT1;
    }
}
