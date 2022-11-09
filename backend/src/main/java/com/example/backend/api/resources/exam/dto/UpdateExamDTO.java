package com.example.backend.api.resources.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateExamDTO {

    private List<QuestionAndAnswerDTO> questionAndAnswerDTOList;

    private Long userID;

    private Long examID;

    private Timestamp creationDate;

    public Optional<List<QuestionAndAnswerDTO>> getQuestionAndAnswerDTOListOptional(List<QuestionAndAnswerDTO> questionAndAnswerDTOList) {
        return Optional
                .ofNullable(questionAndAnswerDTOList);
    }

    public Optional<Long> getUserIDOptional(Long userID) {
        return Optional
                .ofNullable(userID);
    }

    public Optional<Long> getExamIDOptional(Long examID) {
        return Optional
                .ofNullable(examID);
    }

    public Optional<Timestamp> getCreationDateOptional(Timestamp creationDate) {
        return Optional
                .ofNullable(creationDate);
    }

    @Override
    public String toString() {
        return "RepliedExamDTO{" +
                "questionDTOList=" + questionAndAnswerDTOList +
                '}';
    }
}
