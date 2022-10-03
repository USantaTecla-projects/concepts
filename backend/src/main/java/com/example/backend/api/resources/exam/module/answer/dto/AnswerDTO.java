package com.example.backend.api.resources.exam.module.answer.dto;

import com.example.backend.api.resources.exam.module.type.Type;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {
    private Long userID;

    private Type type;

    private JsonNode reply;

    public Optional<Type> getTypeOptional(final Type type) {
        return Optional
                .ofNullable(type);
    }

    public Optional<Long> getUserIDOptional(Long userID) {
        return Optional
                .ofNullable(userID);
    }

    public Optional<JsonNode> getReplyOptional(JsonNode reply) {
        return Optional
                .ofNullable(reply);
    }


}