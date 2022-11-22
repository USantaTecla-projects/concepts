package com.example.backend.unit.knowledge;

import com.example.backend.api.resources.knowledge.justification.CrudJustificationService;
import com.example.backend.api.resources.knowledge.justification.JustificationRepository;
import com.example.backend.api.resources.knowledge.justification.dto.JustificationDTO;
import com.example.backend.api.resources.knowledge.justification.exception.specific.JustificationDTOBadRequestException;
import com.example.backend.api.resources.knowledge.justification.exception.specific.JustificationNotBelongToDefinitionException;
import com.example.backend.api.resources.knowledge.justification.exception.specific.JustificationNotFoundException;
import com.example.backend.api.resources.knowledge.justification.model.Justification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.backend.api.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.api.resources.knowledge.definition.model.Definition;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CrudJustificationServiceTest {

    @Mock
    private DefinitionRepository definitionRepository;

    @Mock
    private JustificationRepository justificationRepository;

    @InjectMocks
    private CrudJustificationService crudJustificationService;

    @Nested
    @DisplayName("CreateOne")
    class JustificationCreateOne {

        @Test
        @DisplayName("Should create a Justification with a correct DTO")
        void createWithCorrectDTO() {
            final long conceptId = 1L;
            final Justification justification = new Justification(3L, "Software Justification", true, null, 1L, 2L);
            final Definition definition = new Definition(2L, "Software answer", true, 1L, new LinkedList<>(List.of(justification)));
            final JustificationDTO justificationDTO = new JustificationDTO("Software Justification", true, null);

            when(justificationRepository.save(any(Justification.class)))
                    .thenReturn(justification);

            when(definitionRepository.save(any(Definition.class)))
                    .thenReturn(definition);

            Justification createdJustification = crudJustificationService.createOne(conceptId, definition, justificationDTO);

            assertEquals(justification.getText(), createdJustification.getText());
            assertEquals(justification.getConceptID(), createdJustification.getConceptID());
            assertEquals(definition.getJustificationList().get(0).getText(), justification.getText());
            assertEquals(definition.getJustificationList().get(0).getDefinitionID(), definition.getId());
        }

        @Test
        @DisplayName("Should not create a Justification with a wrong DTO")
        void createWithWrongDTO() {
            final long conceptId = 1L;
            final JustificationDTO wrongJustificationDTO = new JustificationDTO();
            final Definition definition = new Definition(2L, "Software answer", true, 1L);

            assertThrows(JustificationDTOBadRequestException.class, () -> crudJustificationService.createOne(conceptId, definition, wrongJustificationDTO));
        }
    }

    @Nested
    @DisplayName("FindOne")
    class JustificationFindOne {

        @Test
        @DisplayName("Should find a Justification with the given ID")
        void findOneWhenExists() {
            final Justification justification = new Justification(3L, "Software Justification", true, null, 1L, 2L);
            final Definition definition = new Definition(2L, "Software answer", true, 1L, new LinkedList<>(List.of(justification)));

            when(justificationRepository.findById(justification.getId()))
                    .thenReturn(Optional.of(justification));

            Justification foundJustification = crudJustificationService.findOne(definition, justification.getId());
            assertEquals(justification, foundJustification);
        }

        @Test
        @DisplayName("Should not find a Justification with the given ID in the database")
        void findOneWhenNotExists() {
            final Justification justification = new Justification(3L, "Software Justification", true, null, 1L, 2L);
            final Definition definition = new Definition(2L, "Software answer", true, 1L, new LinkedList<>(List.of(justification)));
            final long wrongJustificationId = 99L;

            when(justificationRepository.findById(wrongJustificationId))
                    .thenReturn(Optional.empty());

            assertThrows(JustificationNotFoundException.class, () -> crudJustificationService.findOne(definition, wrongJustificationId));
        }

        @Test
        @DisplayName("Should not find a Justification with the given ID on the given answer")
        void findOneWhenNotBelongToConcept() {
            final Justification justification = new Justification(3L, "Software Justification", true, null, 1L, 2L);
            final Definition definition = new Definition(2L, "Software answer", true, 1L, new LinkedList<>());


            when(justificationRepository.findById(justification.getId()))
                    .thenReturn(Optional.of(justification));

            assertThrows(JustificationNotBelongToDefinitionException.class, () -> crudJustificationService.findOne(definition, justification.getId()));
        }
    }

    @Nested
    @DisplayName("FindAll")
    class JustificationFindAll {

        @Test
        @DisplayName("Should find the list of Justifications in the given answer")
        void findAllWhenDataExists() {
            final Justification justification = new Justification(3L, "Software Justification", true, null, 1L, 2L);
            final Definition definition = new Definition(2L, "Software answer", true, 1L, new LinkedList<>(List.of(justification)));

            List<Justification> justificationList = crudJustificationService.findAll(definition);
            assertEquals(justificationList.size(), definition.getJustificationList().size());
        }

        @Test
        @DisplayName("Should find an empty List of Justification in the given Definition")
        void findAllWhenDataNotExists() {
            final Definition definition = new Definition(2L, "Software answer", true, 1L, new LinkedList<>());

            List<Justification> justificationList = crudJustificationService.findAll(definition);
            assertEquals(justificationList.size(), 0);
        }

    }

    @Nested
    @DisplayName("UpdateOne")
    class JustificationUpdateOne {

        @Test
        @DisplayName("Should update a Justification if exists")
        void updateWhenExists() {
            final JustificationDTO justificationDTO = new JustificationDTO("Software justification", true, null);
            final Justification justification = new Justification(3L, "Software Justification", true, null, 1L, 2L);
            final Definition definition = new Definition(2L, "Software answer", true, 1L, new LinkedList<>(List.of(justification)));

            when(justificationRepository.findById(justification.getId()))
                    .thenReturn(Optional.of(justification));

            crudJustificationService.updateOne(definition, justification.getId(), justificationDTO);
        }

        @Test
        @DisplayName("Should throw a Exception if the Justification is not found")
        void updateWhenNotExists() {
            final JustificationDTO justificationDTO = new JustificationDTO("Software justification", true, null);
            final Definition definition = new Definition(2L, "Software answer", true, 1L, new LinkedList<>());
            final long wrongJustificationId = 99L;

            assertThrows(JustificationNotFoundException.class, () -> crudJustificationService.updateOne(definition, wrongJustificationId, justificationDTO));
        }
    }

    @Nested
    @DisplayName("DeleteOne")
    class JustificationDelete {

        @Test
        @DisplayName("Should delete a Justification if exists")
        void deleteWhenExists() {
            final Justification justification = new Justification(3L, "Software Justification", true, null, 1L, 2L);
            final Definition definition = new Definition(2L, "Software answer", true, 1L, new LinkedList<>(List.of(justification)));

            when(justificationRepository.findById(justification.getId()))
                    .thenReturn(Optional.of(justification));

            crudJustificationService.removeOne(definition, justification.getId());
        }

        @Test
        @DisplayName("Should throw an Exception if the Justification is not found")
        void deleteWhenNotExists() {
            final Definition definition = new Definition(2L, "Software answer", true, 1L, new LinkedList<>());
            final long wrongJustificationId = 99L;

            assertThrows(JustificationNotFoundException.class, () -> crudJustificationService.removeOne(definition, wrongJustificationId));
        }
    }
}