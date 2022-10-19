package com.example.backend.api.resources.exam.dto;

import com.example.backend.api.resources.exam.domain.family.answer.dto.AnswerDTO;
import com.example.backend.api.resources.exam.domain.family.question.dto.QuestionDTO;

public class QuestionAndAnswerDTO {

    private QuestionDTO questionDTO;

    private AnswerDTO answerDTO;

    public QuestionDTO getQuestionDTO() {
        return questionDTO;
    }

    public void setQuestionDTO(QuestionDTO questionDTO) {
        this.questionDTO = questionDTO;
    }

    public AnswerDTO getAnswerDTO() {
        return answerDTO;
    }

    public void setAnswerDTO(AnswerDTO answerDTO) {
        this.answerDTO = answerDTO;
    }
}
