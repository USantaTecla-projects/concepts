package com.example.backend.api.core.answer.dto;

import com.example.backend.api.core.justification.dto.JustificationResDTO;
import com.example.backend.api.core.justification.model.Justification;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

@Data
@NoArgsConstructor
public class AnswerResDTO {

    private Long id;

    private String text;

    private Boolean isCorrect;

    private CollectionModel<EntityModel<JustificationResDTO>> justifications;

    public AnswerResDTO(Long id, String text, Boolean isCorrect, CollectionModel<EntityModel<JustificationResDTO>> justifications) {
        this.id = id;
        this.text = text;
        this.isCorrect = isCorrect;
        this.justifications = justifications;
    }

    public AnswerResDTO(Long id, String text, Boolean isCorrect) {
        this.id = id;
        this.text = text;
        this.isCorrect = isCorrect;
    }
}
