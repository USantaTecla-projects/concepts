package com.example.backend.api.core.justification;

import com.example.backend.api.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.api.resources.knowledge.definition.model.Definition;
import com.example.backend.api.resources.knowledge.justification.JustificationRepository;
import com.example.backend.api.resources.knowledge.justification.JustificationService;
import com.example.backend.api.resources.knowledge.justification.dto.JustificationDTO;
import com.example.backend.api.resources.knowledge.justification.exception.model.JustificationDTOBadRequestException;
import com.example.backend.api.resources.knowledge.justification.exception.model.JustificationNotBelongToDefinitionException;
import com.example.backend.api.resources.knowledge.justification.exception.model.JustificationNotFoundException;
import com.example.backend.api.resources.knowledge.justification.model.Justification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JustificationServiceTest {

    @Mock
    private DefinitionRepository definitionRepository;

    @Mock
    private JustificationRepository justificationRepository;

    @InjectMocks
    private JustificationService justificationService;

    @Nested
    @DisplayName("POST")
    class JustificationPost {

        @Test
        @DisplayName("(Create) Should create a Justification with a correct DTO")
        void createWithCorrectDTO() {
            final long conceptId = 1L;
            final Justification justification = new Justification(3L, "Software Justification", true, null, 1L, 2L);
            final Definition definition = new Definition(2L, "Software answer", true, 1L, new LinkedList<>(List.of(justification)));
            final JustificationDTO justificationDTO = new JustificationDTO("Software Justification", true, null);

            when(justificationRepository.save(any(Justification.class)))
                    .thenReturn(justification);

            when(definitionRepository.save(any(Definition.class)))
                    .thenReturn(definition);

            Justification createdJustification = justificationService.create(conceptId, definition, justificationDTO);


            // The justification is created correctly
            assertEquals(justification.getText(), createdJustification.getText());
            assertEquals(justification.getConceptID(), createdJustification.getConceptID());

            // The justification is added to the Concept
            assertEquals(
                    definition.getJustificationList().get(0).getText(),
                    justification.getText()
            );
            assertEquals(definition.getJustificationList().get(0).getDefinitionID(), definition.getId());
        }

        @Test
        @DisplayName("(Create) Should not create a justification with a wrong DTO")
        void createWithWrongDTO() {
            final long conceptId = 1L;
            final JustificationDTO wrongJustificationDTO = new JustificationDTO();
            final Definition definition = new Definition(2L, "Software answer", true, 1L);

            assertThrows(JustificationDTOBadRequestException.class, () -> justificationService.create(conceptId, definition, wrongJustificationDTO));
        }
    }

    @Nested
    @DisplayName("GET")
    class JustificationGet {

        @Test
        @DisplayName("(FindOne) Should find an Answer with the given id")
        void findOneWhenExists() {
            final Justification justification = new Justification(3L, "Software Justification", true, null, 1L, 2L);
            final Definition definition = new Definition(2L, "Software answer", true, 1L, new LinkedList<>(List.of(justification)));

            when(justificationRepository.findById(justification.getId()))
                    .thenReturn(Optional.of(justification));

            Justification foundJustification = justificationService.findOne(definition, justification.getId());
            assertEquals(justification, foundJustification);
        }

        @Test
        @DisplayName("(FindOne) Should not find a justification with the given id in the database")
        void findOneWhenNotExists() {
            final Justification justification = new Justification(3L, "Software Justification", true, null, 1L, 2L);
            final Definition definition = new Definition(2L, "Software answer", true, 1L, new LinkedList<>(List.of(justification)));
            final long wrongJustificationId = 99L;

            when(justificationRepository.findById(wrongJustificationId))
                    .thenReturn(Optional.empty());

            assertThrows(JustificationNotFoundException.class, () -> justificationService.findOne(definition, wrongJustificationId));
        }

        @Test
        @DisplayName("(FindOne) Should not find a justification with the given id on the given answer")
        void findOneWhenNotBelongToConcept() {
            final Justification justification = new Justification(3L, "Software Justification", true, null, 1L, 2L);
            final Definition definition = new Definition(2L, "Software answer", true, 1L, new LinkedList<>());


            when(justificationRepository.findById(justification.getId()))
                    .thenReturn(Optional.of(justification));

            assertThrows(JustificationNotBelongToDefinitionException.class, () -> justificationService.findOne(definition, justification.getId()));
        }

        @Test
        @DisplayName("(FindAll) Should find the list of justifications in the given answer")
        void findAllWhenDataExists() {
            final Justification justification = new Justification(3L, "Software Justification", true, null, 1L, 2L);
            final Definition definition = new Definition(2L, "Software answer", true, 1L, new LinkedList<>(List.of(justification)));

            List<Justification> justificationList = justificationService.findAll(definition);
            assertEquals(justificationList.size(), definition.getJustificationList().size());
        }

        @Test
        @DisplayName("(FindAll) Should find an empty list of justification in the given answer")
        void findAllWhenDataNotExists() {
            final Definition definition = new Definition(2L, "Software answer", true, 1L, new LinkedList<>());

            List<Justification> justificationList = justificationService.findAll(definition);
            assertEquals(justificationList.size(), 0);
        }

    }

    @Nested
    @DisplayName("PUT")
    class JustificationPut {

        @Test
        @DisplayName("(Update) Should update a justification if exists")
        void updateWhenExists() {
            final JustificationDTO justificationDTO = new JustificationDTO("Software justification", true, null);
            final Justification justification = new Justification(3L, "Software Justification", true, null, 1L, 2L);
            final Definition definition = new Definition(2L, "Software answer", true, 1L, new LinkedList<>(List.of(justification)));

            when(justificationRepository.findById(justification.getId()))
                    .thenReturn(Optional.of(justification));

            justificationService.updateOne(definition, justification.getId(), justificationDTO);
        }

        @Test
        @DisplayName("(Update) Should throw an exception if the justification is not found")
        void updateWhenNotExists() {
            final JustificationDTO justificationDTO = new JustificationDTO("Software justification", true, null);
            final Definition definition = new Definition(2L, "Software answer", true, 1L, new LinkedList<>());
            final long wrongJustificationId = 99L;

            assertThrows(JustificationNotFoundException.class, () -> justificationService.updateOne(definition, wrongJustificationId, justificationDTO));
        }
    }

    @Nested
    @DisplayName("DELETE")
    class JustificationDelete {

        @Test
        @DisplayName("(Delete) Should delete a justification if exists")
        void deleteWhenExists() {
            final Justification justification = new Justification(3L, "Software Justification", true, null, 1L, 2L);
            final Definition definition = new Definition(2L, "Software answer", true, 1L, new LinkedList<>(List.of(justification)));

            when(justificationRepository.findById(justification.getId()))
                    .thenReturn(Optional.of(justification));

            justificationService.removeOne(definition, justification.getId());
        }

        @Test
        @DisplayName("(Delete) Should throw an exception if the justification is not found")
        void deleteWhenNotExists() {
            final Definition definition = new Definition(2L, "Software answer", true, 1L, new LinkedList<>());
            final long wrongJustificationId = 99L;

            assertThrows(JustificationNotFoundException.class, () -> justificationService.removeOne(definition, wrongJustificationId));
        }
    }
}
