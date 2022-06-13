package com.example.backend.api.core.answer.dto;

import lombok.Data;

import java.util.Optional;

@Data
public class AnswerReqDTO {

    private String text;

    private Boolean isCorrect;

    public AnswerReqDTO() {
    }

    public AnswerReqDTO(String text, Boolean isCorrect) {
        this.text = text;
        this.isCorrect = isCorrect;
    }

    public AnswerReqDTO(String text) {
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
