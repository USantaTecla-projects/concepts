package com.example.backend.api.core.concept.dto;

import lombok.Data;

import java.util.List;

@Data
public class ConceptDTO {

    private String text;

    private List<Long> answers;

    public ConceptDTO(String text, List<Long> answers) {
        this.text = text;
        this.answers = answers;
    }
}
