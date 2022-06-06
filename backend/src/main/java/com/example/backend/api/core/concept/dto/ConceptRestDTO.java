package com.example.backend.api.core.concept.dto;

import com.example.backend.api.core.answer.model.Answer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;

@Data
@NoArgsConstructor
public class ConceptRestDTO {

    private Long id;

    private String text;

    private CollectionModel<EntityModel<Answer>> answers;



    public ConceptRestDTO(Long id, String text) {
        this.id = id;
        this.text = text;
    }
}
