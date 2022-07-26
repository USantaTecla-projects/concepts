package com.example.backend.api.core.concept;

import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.dto.ConceptDTO;
import com.example.backend.api.resources.knowledge.concept.exception.model.ConceptDTOBadRequestException;
import com.example.backend.api.resources.knowledge.concept.exception.model.ConceptNotFoundException;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import com.example.backend.api.resources.knowledge.concept.ConceptService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConceptServiceTest {

    @Mock
    private ConceptRepository conceptRepository;

    @InjectMocks
    private ConceptService conceptService;

    @Nested
    @DisplayName("POST")
    class ConceptPost {
        @Test
        @DisplayName("(Create) Should create a Concept with a correct DTO")
        void createWithCorrectDTO() {
            final Concept concept = new Concept(1L, "Software", new LinkedList<>());
            final ConceptDTO conceptDTO = new ConceptDTO("Software");

            when(conceptRepository.save(any(Concept.class)))
                    .thenReturn(concept);

            Concept createdConcept = conceptService.create(conceptDTO);

            assertEquals(concept, createdConcept);
        }

        @Test
        @DisplayName("(Create) Should not create a Concept with an incorrect DTO")
        void createWithWrongDTO() {
            final ConceptDTO wrongConceptDTO = new ConceptDTO();

            assertThrows(ConceptDTOBadRequestException.class, () -> conceptService.create(wrongConceptDTO));
        }
    }

    @Nested
    @DisplayName("GET")
    class ConceptGet {
        @Test
        @DisplayName("(FindOne) Should find one Concept with the given id")
        void findOneThatExist() {
            final Concept concept = new Concept(1L, "Software", new LinkedList<>());

            when(conceptRepository.findById(concept.getId()))
                    .thenReturn(Optional.of(concept));

            final Concept foundConcept = conceptService.findOne(concept.getId());

            assertEquals(concept, foundConcept);
        }

        @Test
        @DisplayName("(FindOne) Should throw an Exception with the given id")
        void findOneThatNotExist() {
            assertThrows(ConceptNotFoundException.class, () -> conceptService.findOne(2L));
        }

        @Test
        @DisplayName("(FindAll) Should find a page with 3 Concepts")
        void findAllWhenDataExists() {
            final Concept concept1 = new Concept(1L, "Software", new LinkedList<>());
            final Concept concept2 = new Concept(3L, "Hardware", new LinkedList<>());
            final Concept concept3 = new Concept(5L, "Functional Programming", new LinkedList<>());
            final Page<Concept> conceptPage = new PageImpl<>(List.of(concept1, concept2, concept3));
            final int pageNumber = 0;
            final int pageSize = 5;

            when(conceptRepository.findAll(PageRequest.of(pageNumber, pageSize)))
                    .thenReturn(conceptPage);

            Page<Concept> foundPage = conceptService.findAll(pageNumber);
            assertEquals(foundPage.getNumberOfElements(), 3);
        }

        @Test
        @DisplayName("(FindAll) Should find an empty Page")
        void findAllWhenDataNotExists() {
            final int wrongPageNumber = 99;
            final int pageSize = 5;

            when(conceptRepository.findAll(PageRequest.of(wrongPageNumber, pageSize)))
                    .thenReturn(new PageImpl<>(Collections.emptyList()));

            Page<Concept> foundPage = conceptService.findAll(99);
            assertEquals(foundPage.getNumberOfElements(), 0);
        }
    }

    @Nested
    @DisplayName("PUT")
    class ConceptPut {
        @Test
        @DisplayName("(Update) Should update a Concept if exists")
        void updateWhenExists() {
            final ConceptDTO conceptDTO = new ConceptDTO("Software");
            final Concept concept = new Concept(1L, "Software", new LinkedList<>());


            when(conceptRepository.findById(concept.getId()))
                    .thenReturn(Optional.of(concept));

            conceptService.updateOne(concept.getId(), conceptDTO);
        }

        @Test
        @DisplayName("(Update) Should throw an exception if the Concept is not found")
        void updateWhenNotExists() {
            final ConceptDTO conceptDTO = new ConceptDTO("Software");
            final long wrongConceptId = 2L;

            when(conceptRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            assertThrows(ConceptNotFoundException.class, () -> conceptService.updateOne(wrongConceptId, conceptDTO));
        }
    }

    @Nested
    @DisplayName("DELETE")
    class ConceptDelete {
        @Test
        @DisplayName("(Delete) Should delete a Concept if exists")
        void deleteWhenExists() {
            final Concept concept = new Concept(1L, "Software", new LinkedList<>());

            when(conceptRepository.findById(concept.getId()))
                    .thenReturn(Optional.of(concept));

            conceptService.removeOne(concept.getId());
        }

        @Test
        @DisplayName("(Delete) Should throw an exception if the Concept is not found")
        void deleteWhenNotExists() {
            final long wrongConceptId = 2L;

            when(conceptRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            assertThrows(ConceptNotFoundException.class, () -> conceptService.removeOne(wrongConceptId));
        }
    }
}