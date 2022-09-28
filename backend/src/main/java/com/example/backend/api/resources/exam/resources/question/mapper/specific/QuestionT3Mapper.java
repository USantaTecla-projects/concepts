package com.example.backend.api.resources.exam.resources.question.mapper.specific;

import com.example.backend.api.resources.exam.dto.QuestionDTO;
import com.example.backend.api.resources.exam.resources.question.mapper.QuestionMapper;
import com.example.backend.api.resources.exam.resources.question.model.Question;
import com.example.backend.api.resources.exam.resources.question.model.specific.QuestionT3;

public class QuestionT3Mapper extends QuestionMapper {

    @Override
    protected Question createQuestionFromDTO(QuestionDTO questionDTO) {
        Long conceptID = Long.valueOf((Integer) getTypeDetail("conceptId"));
        Long definitionID = Long.valueOf((Integer) getTypeDetail("definitionId"));
        Long justificationID = Long.valueOf((Integer) getTypeDetail("justificationId"));

        QuestionT3 questionT3 = new QuestionT3();
        questionT3.setConceptID(conceptID);
        questionT3.setDefinitionID(definitionID);
        questionT3.setJustificationID(justificationID);

        return questionT3;
    }
}
