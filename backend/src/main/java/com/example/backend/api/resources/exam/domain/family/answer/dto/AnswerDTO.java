package com.example.backend.api.resources.exam.domain.family.answer.dto;

import com.example.backend.api.resources.exam.domain.factory.Type;
import com.example.backend.api.resources.exam.domain.family.answer.model.CorrectionStatus;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {
    private Long id;

    private Long userID;

    private Long questionID;

    private Type type;

    private CorrectionStatus correctionStatus;

    private JsonNode reply;

    public Optional<Long> getIDOptional(Long id) {
        return Optional
                .ofNullable(id);
    }

    public Optional<Type> getTypeOptional(final Type type) {
        return Optional
                .ofNullable(type);
    }

    public Optional<Long> getUserIDOptional(Long userID) {
        return Optional
                .ofNullable(userID);
    }

    public Optional<Long> getQuestionIDOptional(Long questionID) {
        return Optional
                .ofNullable(questionID);
    }


    public Optional<CorrectionStatus> getCorrectionStatusOptional(CorrectionStatus correctionStatus) {
        return Optional
                .ofNullable(correctionStatus);
    }


    public Optional<JsonNode> getReplyOptional(JsonNode reply) {
        return Optional
                .ofNullable(reply);
    }


}
