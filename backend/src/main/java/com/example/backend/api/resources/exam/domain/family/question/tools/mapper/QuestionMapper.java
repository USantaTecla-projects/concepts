package com.example.backend.api.resources.exam.domain.family.question.tools.mapper;

import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.dto.QuestionDTO;
import com.example.backend.api.resources.exam.domain.family.question.exception.specific.QuestionTypeDetailsNotFoundException;

import java.util.Map;

public abstract class QuestionMapper {

    protected QuestionDTO questionDTO;

    private Map<String, Object> questionTypeDetails;

    public Question mapQuestion(QuestionDTO questionDTO){
        this.questionDTO = questionDTO;
        setQuestionTypeDetails();
        return getQuestionFromDTO();
    };

    protected abstract Question getQuestionFromDTO();

    protected Object getTypeDetail(String key) {
        Object value = questionTypeDetails.get(key);
        if (value == null) {
            throw new QuestionTypeDetailsNotFoundException("No question type detail " + key + " found in the DTO for questionType");
        }
        return value;
    }

    private void setQuestionTypeDetails() {
        this.questionTypeDetails = questionDTO
                .getQuestionTypeDetailsOptional()
                .orElseThrow(() -> new QuestionTypeDetailsNotFoundException("No question type details where found in the DTO"));
    }
}
