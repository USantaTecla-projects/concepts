package com.example.backend.e2e.resources.knowledge.justification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JustificationDTO {

    private String text;

    private Boolean correct;

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
}
