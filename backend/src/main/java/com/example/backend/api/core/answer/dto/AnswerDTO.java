package com.example.backend.api.core.answer.dto;

import com.example.backend.api.core.justification.models.Justification;
import lombok.Data;

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
}