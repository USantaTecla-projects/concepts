package com.example.backend.e2e.resources.exam.dto;

import com.example.backend.e2e.resources.exam.domain.family.answer.dto.AnswerDTO;
import com.example.backend.e2e.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.e2e.resources.exam.domain.family.question.dto.QuestionDTO;
import com.example.backend.e2e.resources.exam.domain.family.question.model.Question;
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

    private Long userID;

    private Long examID;

    private Timestamp creationDate;

    private Boolean corrected;

    private List<QuestionDTO> questionDTOList;

    private List<AnswerDTO> answerDTOList;

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

    public Optional<Boolean> getCorrectedOptional(Boolean corrected) {
        return Optional
                .ofNullable(corrected);
    }

    public Optional<List<QuestionDTO>> getQuestionDTOListOptional(List<QuestionDTO> questionDTOList){
        return Optional
                .ofNullable(questionDTOList);
    }

    public Optional<List<AnswerDTO>> getAnswerDTOListOptional(List<AnswerDTO> answerDTOList){
        return Optional
                .ofNullable(answerDTOList);
    }
}
