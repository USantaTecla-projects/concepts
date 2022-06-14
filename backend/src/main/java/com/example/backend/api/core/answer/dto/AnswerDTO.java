package com.example.backend.api.core.answer.dto;

import lombok.Data;

import java.util.Optional;

@Data
public class AnswerDTO {

    private String text;

    private Boolean isCorrect;

    public AnswerDTO() {
    }

    public AnswerDTO(String text, Boolean isCorrect) {
        this.text = text;
        this.isCorrect = isCorrect;
    }

    public AnswerDTO(String text) {
        this.text = text;
    }

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
