package com.example.backend.api.core.justification.dto;

import java.util.Optional;

public class JustificationReqDTO {

    private String text;

    private Boolean isCorrect;

    private String error;


    public Optional<String> getTextOptional(final String text) {
        return Optional
                .ofNullable(text)
                .filter(t -> !t.isEmpty());
    }

    public Optional<Boolean> getCorrectOptional(final Boolean isCorrect) {
        return Optional
                .ofNullable(isCorrect);
    }


    public Optional<String> getErrorOptional(final String error) {
        return getTextOptional(error);
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
