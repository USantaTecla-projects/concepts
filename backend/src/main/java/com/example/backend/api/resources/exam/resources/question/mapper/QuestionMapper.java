package com.example.backend.api.resources.exam.resources.question.mapper;

import com.example.backend.api.resources.exam.dto.QuestionDTO;
import com.example.backend.api.resources.exam.resources.question.exception.model.QuestionTypeDetailsNotFoundException;
import com.example.backend.api.resources.exam.resources.question.model.Question;

import java.util.Map;

public abstract class QuestionMapper {

    private Map<String, Object> questionTypeDetails;

    public Question mapQuestion(QuestionDTO questionDTO){
        setQuestionTypeDetails(questionDTO);
        return createQuestionFromDTO(questionDTO);
    };

    protected abstract Question createQuestionFromDTO(QuestionDTO questionDTO);

    protected Object getTypeDetail(String key) {
        Object value = questionTypeDetails.get(key);
        if (value == null) {
            throw new QuestionTypeDetailsNotFoundException("No question type detail " + key + " found in the DTO for questionType");
        }
        return value;
    }

    private void setQuestionTypeDetails(QuestionDTO questionDTO) {
        this.questionTypeDetails = questionDTO
                .getQuestionTypeDetailsOptional()
                .orElseThrow(() -> new QuestionTypeDetailsNotFoundException("No question type details where found in the DTO"));
    }
}
