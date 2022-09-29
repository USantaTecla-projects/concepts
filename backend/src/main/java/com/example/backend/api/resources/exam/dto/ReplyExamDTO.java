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

    private List<QuestionAndAnswerDTO> questionAndAnswerDTOList;

    private Long userID;

    public List<QuestionAndAnswerDTO> getQuestionDTOList() {
        return questionAndAnswerDTOList;
    }

    public void setQuestionDTOList(List<QuestionAndAnswerDTO> questionDTOList) {
        this.questionAndAnswerDTOList = questionDTOList;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Optional <List<QuestionAndAnswerDTO>> getQuestionAndAnswerDTOListOptional(List<QuestionAndAnswerDTO> questionAndAnswerDTOList) {
        return Optional
                .ofNullable(questionAndAnswerDTOList);
    }

    public Optional <Long> getUserIDOptional(Long userID) {
        return Optional
                .ofNullable(userID);
    }

    @Override
    public String toString() {
        return "RepliedExamDTO{" +
                "questionDTOList=" + questionAndAnswerDTOList +
                '}';
    }
}
