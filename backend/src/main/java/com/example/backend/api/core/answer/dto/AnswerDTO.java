package com.example.backend.api.core.answer.dto;

import com.example.backend.api.core.justification.models.Justification;
import lombok.Data;

@Data
public class AnswerDTO {

    private String text;

    private Boolean isCorrect;

    private Integer conceptId;

    private Justification justification;

}
