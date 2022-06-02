package com.example.backend.api.core.concept.service;

import com.example.backend.api.core.answer.AnswerRepository;
import com.example.backend.api.core.concept.ConceptRepository;
import com.example.backend.api.core.concept.dto.ConceptDTO;
import com.example.backend.api.core.concept.exception.model.ConceptNotFoundException;
import com.example.backend.api.core.concept.model.Concept;
import com.example.backend.api.core.concept.exception.model.ConceptDTOBadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class ConceptServiceTest {

    @Mock
    private ConceptRepository conceptRepository;

    @Mock
    private AnswerRepository answerRepository;

    @InjectMocks
    private ConceptService conceptService;

    private final Concept concept1 = new Concept(1L, "Software", new LinkedList<>());
    private final Concept concept2 = new Concept(2L, "Hardware", new LinkedList<>());
    private final Concept concept3 = new Concept(3L, "Functional Programming", new LinkedList<>());

    private final ConceptDTO conceptDTO1 = new ConceptDTO("Software", new LinkedList<>());
    private final ConceptDTO wrongConceptDTO = new ConceptDTO();

    private final Page<Concept> conceptPage = new PageImpl(List.of(concept1, concept2, concept3));

    @BeforeEach
    void setUpMocks() {
        lenient().when(conceptRepository.save(any(Concept.class))).thenReturn(concept1);

        lenient().when(conceptRepository.findById(1L)).thenReturn(Optional.of(concept1));
        lenient().when(conceptRepository.findById(2L)).thenReturn(Optional.empty());

        lenient().when(conceptRepository.findAll(PageRequest.of(0, 5))).thenReturn(conceptPage);
        lenient().when(conceptRepository.findAll(PageRequest.of(1, 5))).thenReturn(new PageImpl<>(List.of()));
    }

    @Nested
    @DisplayName("POST")
    class ConceptPost {
        @Test
        @DisplayName("(Create) Should create a Concept with a correct DTO")
        void createWithCorrectDTO() {
            Concept createdConcept = conceptService.create(conceptDTO1);

            assertEquals(concept1, createdConcept);
        }

        @Test
        @DisplayName("(Create) Should not create a Concept with an incorrect DTO")
        void createWithWrongDTO() {
            assertThrows(ConceptDTOBadRequestException.class, () -> conceptService.create(wrongConceptDTO));
        }
    }

    @Nested
    @DisplayName("GET")
    class ConceptGet {
        @Test
        @DisplayName("(FindOne) Should find one Concept with the given id")
        void findOneThatExist() {
            Concept concept = conceptService.findOne(1L);
            assertEquals(concept, concept1);
        }

        @Test
        @DisplayName("(FindOne) Should throw an Exception with the given id")
        void findOneThatNotExist() {
            assertThrows(ConceptNotFoundException.class, () -> conceptService.findOne(2L));
        }

        @Test
        @DisplayName("(FindAll) Should find a page with 3 Concepts")
        void findAllWithData() {
            Page<Concept> conceptPage = conceptService.findAll(0);
            assertEquals(conceptPage.getNumberOfElements(), 3);
        }

        @Test
        @DisplayName("(FindAll) Should find an empty Page")
        void findAllWithNoData() {
            Page<Concept> conceptPage = conceptService.findAll(1);
            assertEquals(conceptPage.getNumberOfElements(), 0);
        }
    }


    @Nested
    @DisplayName("PUT")
    class ConceptPut {
        @Test
        @DisplayName("(Update) Should update a Concept if exists")
        void updateWhenExists() {
            conceptService.updateOne(1L, conceptDTO1);
        }

        @Test
        @DisplayName("(Update) Should throw an exception if the Concept is not found")
        void updateWhenNotExists() {
            assertThrows(ConceptNotFoundException.class, () -> conceptService.updateOne(2L, conceptDTO1));
        }
    }

    @Nested
    @DisplayName("DELETE")
    class ConceptDelete {
        @Test
        @DisplayName("(Delete) Should delete a Concept if exists")
        void deleteWhenExists() {
            conceptService.removeOne(1L);
        }

        @Test
        @DisplayName("(Delete) Should throw an exception if the Concept is not found")
        void deleteWhenNotExists() {
            assertThrows(ConceptNotFoundException.class, () -> conceptService.removeOne(2L));
        }
    }
}