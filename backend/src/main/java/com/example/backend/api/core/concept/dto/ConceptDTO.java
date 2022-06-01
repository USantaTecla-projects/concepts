package com.example.backend.api.core.concept.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConceptDTO {

    private String text;

    private List<Long> answers;

}
