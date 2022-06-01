package com.example.backend.api.core.concept.service;

import com.example.backend.api.core.answer.AnswerRepository;
import com.example.backend.api.core.concept.ConceptRepository;
import com.example.backend.api.core.concept.dto.ConceptDTO;
import com.example.backend.api.core.concept.model.Concept;
import com.example.backend.api.exception.model.DTOBadRequest.DTOBadRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class ConceptServiceTest {

    @Mock
    ConceptRepository conceptRepository;

    @Mock
    AnswerRepository answerRepository;

    @InjectMocks
    ConceptService conceptService;

    Concept concept = new Concept(1L, "Software", new LinkedList<>());

    @BeforeEach
    void setUpMocks() {
        lenient().when(conceptRepository.save(any(Concept.class))).thenReturn(concept);
    }

    @Test
    @DisplayName("Should create a Concept with a correct DTO")
    void createWithCorrectDTO() {
        ConceptDTO conceptDTO = new ConceptDTO("Software", new LinkedList<>());

        Concept createdConcept = conceptService.create(conceptDTO);

        assertEquals(concept, createdConcept);
    }

    @Test
    @DisplayName("Should not create a Concept with an incorrect DTO")
    void createWithWrongDTO(){
        ConceptDTO wrongConceptDTO = new ConceptDTO();

        assertThrows(DTOBadRequest.class, () -> conceptService.create(wrongConceptDTO));
    }
}