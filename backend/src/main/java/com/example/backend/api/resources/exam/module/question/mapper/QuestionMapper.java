package com.example.backend.api.resources.exam.module.question.mapper;

import com.example.backend.api.resources.exam.module.question.dto.QuestionDTO;
import com.example.backend.api.resources.exam.module.question.exception.specific.QuestionTypeDetailsNotFoundException;
import com.example.backend.api.resources.exam.module.question.model.Question;

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
