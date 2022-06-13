package com.example.backend.api.core.justification.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JustificationResDTO {

    private Long id;

    private String text;

    private Boolean isCorrect;

    private String error;

}
