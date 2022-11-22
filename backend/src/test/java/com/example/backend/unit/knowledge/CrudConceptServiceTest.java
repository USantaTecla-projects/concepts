package com.example.backend.unit.knowledge;

import com.example.backend.api.resources.knowledge.concept.CrudConceptService;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.dto.ConceptDTO;
import com.example.backend.api.resources.knowledge.concept.exception.specific.ConceptDTOBadRequestException;
import com.example.backend.api.resources.knowledge.concept.exception.specific.ConceptNotFoundException;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
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

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CrudConceptServiceTest {

    @Mock
    private ConceptRepository conceptRepository;

    @InjectMocks
    private CrudConceptService crudConceptService;

    @Nested
    @DisplayName("CreateOne")
    class ConceptCreateOne {

        @Test
        @DisplayName("Should create a Concept with a correct DTO")
        void createWithCorrectDTO() {
            final Concept concept = new Concept(1L, "Software", new LinkedList<>());
            final ConceptDTO conceptDTO = new ConceptDTO("Software");

            when(conceptRepository.save(any(Concept.class)))
                    .thenReturn(concept);

            Concept createdConcept = crudConceptService.createOne(conceptDTO);

            assertEquals(concept, createdConcept);
        }

        @Test
        @DisplayName("Should not create a Concept with an incorrect DTO")
        void createWithWrongDTO() {
            final ConceptDTO wrongConceptDTO = new ConceptDTO();

            assertThrows(ConceptDTOBadRequestException.class, () -> crudConceptService.createOne(wrongConceptDTO));
        }
    }

    @Nested
    @DisplayName("FindOne")
    class ConceptFindOne {
        @Test
        @DisplayName("Should find one Concept with the given id")
        void findOneThatExist() {
            final Concept concept = new Concept(1L, "Software", new LinkedList<>());

            when(conceptRepository.findById(concept.getId()))
                    .thenReturn(Optional.of(concept));

            final Concept foundConcept = crudConceptService.findOne(concept.getId());

            assertEquals(concept, foundConcept);
        }

        @Test
        @DisplayName("Should throw an Exception with the given id")
        void findOneThatNotExist() {
            assertThrows(ConceptNotFoundException.class, () -> crudConceptService.findOne(2L));
        }
    }

    @Nested
    @DisplayName("FindAll")
    class ConceptFindAll {

        @Test
        @DisplayName("Should find a page with 3 Concepts")
        void findAllWhenDataExists() {
            final Concept concept1 = new Concept(1L, "Software", new LinkedList<>());
            final Concept concept2 = new Concept(3L, "Hardware", new LinkedList<>());
            final Concept concept3 = new Concept(5L, "Functional Programming", new LinkedList<>());

            final Page<Concept> conceptPage = new PageImpl<>(List.of(concept1, concept2, concept3));
            final int pageNumber = 0;
            final int pageSize = 10;

            when(conceptRepository.findAll(PageRequest.of(pageNumber, pageSize)))
                    .thenReturn(conceptPage);

            Page<Concept> foundPage = crudConceptService.findAll(pageNumber);
            assertEquals(foundPage.getNumberOfElements(), 3);
        }

        @Test
        @DisplayName("Should find an empty Page")
        void findAllWhenDataNotExists() {
            final int wrongPageNumber = 99;
            final int pageSize = 10;

            when(conceptRepository.findAll(PageRequest.of(wrongPageNumber, pageSize)))
                    .thenReturn(new PageImpl<>(Collections.emptyList()));

            Page<Concept> foundPage = crudConceptService.findAll(99);
            assertEquals(foundPage.getNumberOfElements(), 0);
        }
    }

    @Nested
    @DisplayName("UpdateOne")
    class ConceptUpdateOne {
        @Test
        @DisplayName("Should update a Concept if exists")
        void updateWhenExists() {
            final ConceptDTO conceptDTO = new ConceptDTO("Software");
            final Concept concept = new Concept(1L, "Software", new LinkedList<>());


            when(conceptRepository.findById(concept.getId()))
                    .thenReturn(Optional.of(concept));

            crudConceptService.updateOne(concept.getId(), conceptDTO);
        }

        @Test
        @DisplayName("Should throw an exception if the Concept is not found")
        void updateWhenNotExists() {
            final ConceptDTO conceptDTO = new ConceptDTO("Software");
            final long wrongConceptId = 2L;

            when(conceptRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            assertThrows(ConceptNotFoundException.class, () -> crudConceptService.updateOne(wrongConceptId, conceptDTO));
        }
    }

    @Nested
    @DisplayName("RemoveOne")
    class ConceptDeleteOne {
        @Test
        @DisplayName("Should delete a Concept if exists")
        void deleteWhenExists() {
            final Concept concept = new Concept(1L, "Software", new LinkedList<>());

            when(conceptRepository.findById(concept.getId()))
                    .thenReturn(Optional.of(concept));

            crudConceptService.removeOne(concept.getId());
        }

        @Test
        @DisplayName("Should throw an exception if the Concept is not found")
        void deleteWhenNotExists() {
            final long wrongConceptId = 2L;

            when(conceptRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            assertThrows(ConceptNotFoundException.class, () -> crudConceptService.removeOne(wrongConceptId));
        }
    }
}
