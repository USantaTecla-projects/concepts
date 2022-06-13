package com.example.backend.api.core.concept.dto;

import com.example.backend.api.core.answer.dto.AnswerResDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

@Data
@NoArgsConstructor
public class ConceptResDTO {

    private Long id;
    private String text;

    private CollectionModel<EntityModel<AnswerResDTO>> answers;


    public ConceptResDTO(Long id, String text) {
        this.id = id;
        this.text = text;
    }
}
