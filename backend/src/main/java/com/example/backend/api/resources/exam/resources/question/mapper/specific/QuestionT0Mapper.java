package com.example.backend.api.resources.exam.resources.question.mapper.specific;

import com.example.backend.api.resources.exam.dto.QuestionDTO;
import com.example.backend.api.resources.exam.resources.question.mapper.QuestionMapper;
import com.example.backend.api.resources.exam.resources.question.model.Question;
import com.example.backend.api.resources.exam.resources.question.model.specific.QuestionT0;

public class QuestionT0Mapper extends QuestionMapper {

    @Override
    protected Question createQuestionFromDTO(QuestionDTO questionDTO) {


        Long conceptID = Long.valueOf((Integer) getTypeDetail("conceptId"));
        String conceptText = (String) getTypeDetail("conceptText");

        QuestionT0 questionT0 = new QuestionT0();
        questionT0.setConceptID(conceptID);
        questionT0.setConceptText(conceptText);

        return questionT0;
    }


}
