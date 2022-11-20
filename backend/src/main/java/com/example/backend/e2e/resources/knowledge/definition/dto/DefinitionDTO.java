package com.example.backend.e2e.resources.knowledge.definition.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefinitionDTO {

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
