package com.example.backend.api.resources.knowledge.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {

    private String text;

    private Boolean correct;

    public Optional<String> getTextOptional(final String text) {
        return Optional
                .ofNullable(text)
                .filter(t -> !t.isEmpty());
    }

    public Optional<Boolean> getIsCorrectOptional(final Boolean isCorrect) {
        return Optional
                .ofNullable(isCorrect);
    }
}
