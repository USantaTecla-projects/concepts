package com.example.backend.api.resources.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyExamDTO {

    private List<QuestionDTO> questionDTOList;

    public List<QuestionDTO> getQuestionDTOList() {
        return questionDTOList;
    }

    public void setQuestionDTOList(List<QuestionDTO> questionDTOList) {
        this.questionDTOList = questionDTOList;
    }

    public Optional <List<QuestionDTO>> getQuestionDTOListOptional(List<QuestionDTO> questionDTOList) {
        return Optional
                .ofNullable(questionDTOList);
    }

    @Override
    public String toString() {
        return "RepliedExamDTO{" +
                "questionDTOList=" + questionDTOList +
                '}';
    }
}
