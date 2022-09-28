package com.example.backend.api.resources.exam.resources.question.mapper.specific;

import com.example.backend.api.resources.exam.dto.QuestionDTO;
import com.example.backend.api.resources.exam.resources.question.mapper.QuestionMapper;
import com.example.backend.api.resources.exam.resources.question.model.Question;
import com.example.backend.api.resources.exam.resources.question.model.specific.QuestionT2;

public class QuestionT2Mapper extends QuestionMapper {


    @Override
    protected Question createQuestionFromDTO(QuestionDTO questionDTO) {
        Long conceptID = Long.valueOf((Integer) getTypeDetail("conceptId"));
        Long definitionID = Long.valueOf((Integer) getTypeDetail("definitionId"));

        QuestionT2 questionT2 = new QuestionT2();
        questionT2.setConceptID(conceptID);
        questionT2.setDefinitionID(definitionID);

        return questionT2;
    }
}
