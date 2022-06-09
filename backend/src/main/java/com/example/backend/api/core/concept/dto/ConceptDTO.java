package com.example.backend.api.core.concept.dto;

import com.example.backend.api.core.answer.dto.AnswerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConceptDTO {

    private String text;

    public Optional<String> getTextOptional(final String text) {
        return Optional
                .ofNullable(text)
                .filter(t -> !t.isEmpty());
    }

}
