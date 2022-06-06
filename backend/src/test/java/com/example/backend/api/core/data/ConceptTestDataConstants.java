package com.example.backend.api.core.data;

import com.example.backend.api.core.concept.dto.ConceptDTO;
import com.example.backend.api.core.concept.model.Concept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.example.backend.api.core.data.AnswerTestDataConstants.answer1;
import static com.example.backend.api.core.data.AnswerTestDataConstants.answer2;

public final class ConceptTestDataConstants {

    private ConceptTestDataConstants() {
    }

    // Simple Concept DTOs

    public static final ConceptDTO conceptDTO1 = new ConceptDTO("Software");
    public static final ConceptDTO conceptDTO2 = new ConceptDTO("Hardware");
    public static final ConceptDTO conceptDTO3 = new ConceptDTO("Functional Programming");

    // Wrong Concept DTO

    public static final ConceptDTO wrongConceptDTO = new ConceptDTO();

    // Concepts with only text

    public static final Concept concept1 = new Concept(1L, "Software", new LinkedList<>());
    public static final Concept concept2 = new Concept(3L, "Hardware", new LinkedList<>());
    public static final Concept concept3 = new Concept(5L, "Functional Programming", new LinkedList<>());

    // Page of Concepts with only text

    public static final Page<Concept> conceptPage = new PageImpl(List.of(concept1, concept2, concept3));

    // Concepts with linked answers

    public static  final  Concept concept1Answer1 = new Concept(1L,"Software", new LinkedList<>(List.of(answer1)));
    public static  final  Concept concept2Answer2 = new Concept(2L,"Software", new LinkedList<>(List.of(answer2)));
}
