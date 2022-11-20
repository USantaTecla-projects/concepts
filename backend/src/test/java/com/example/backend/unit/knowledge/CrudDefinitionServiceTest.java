package com.example.backend.unit.knowledge;

import com.example.backend.e2e.resources.knowledge.concept.ConceptRepository;
import com.example.backend.e2e.resources.knowledge.concept.model.Concept;
import com.example.backend.e2e.resources.knowledge.definition.CrudDefinitionService;
import com.example.backend.e2e.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.e2e.resources.knowledge.definition.dto.DefinitionDTO;
import com.example.backend.e2e.resources.knowledge.definition.exception.specific.DefinitionDTOBadRequestException;
import com.example.backend.e2e.resources.knowledge.definition.exception.specific.DefinitionNotBelongToConceptException;
import com.example.backend.e2e.resources.knowledge.definition.exception.specific.DefinitionNotFoundException;
import com.example.backend.e2e.resources.knowledge.definition.model.Definition;
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
public class CrudDefinitionServiceTest {

    @Mock
    private ConceptRepository conceptRepository;

    @Mock
    private DefinitionRepository definitionRepository;

    @InjectMocks
    private CrudDefinitionService crudDefinitionService;


    @Nested
    @DisplayName("CreateOne")
    class DefinitionCreate {

        @Test
        @DisplayName("Should create a Definition with a correct DTO")
        void createWithCorrectDTO() {
            final DefinitionDTO definitionDTO = new DefinitionDTO("Software Definition", true);
            final Definition definition = new Definition(2L, "Software Definition", true, 1L);
            final Concept concept = new Concept(1L, "Software", new LinkedList<>(List.of(definition)));

            when(definitionRepository.save(any(Definition.class)))
                    .thenReturn(definition);

            Definition createdDefinition = crudDefinitionService.createOne(concept, definitionDTO);

            assertEquals(definition.getText(), createdDefinition.getText());
            assertEquals(definition.getConceptID(), createdDefinition.getConceptID());
            assertEquals(concept.getDefinitionList().get(0).getText(), definition.getText());
            assertEquals(concept.getDefinitionList().get(0).getConceptID(), concept.getId());
        }

        @Test
        @DisplayName("Should not create a Definition with a wrong DTO")
        void createWithWrongDTO() {
            final DefinitionDTO wrongDefinitionDTO = new DefinitionDTO();
            final Definition definition = new Definition(2L, "Software Definition", true, 1L);
            final Concept concept = new Concept(1L, "Software", new LinkedList<>(List.of(definition)));

            assertThrows(DefinitionDTOBadRequestException.class, () -> crudDefinitionService.createOne(concept, wrongDefinitionDTO));
        }
    }

    @Nested
    @DisplayName("FindOne")
    class DefinitionFindOne {

        @Test
        @DisplayName("Should find a Definition with the given id")
        void findOneWhenExists() {
            final Definition definition = new Definition(2L, "Software Definition", true, 1L);
            final Concept concept = new Concept(1L, "Software", new LinkedList<>(List.of(definition)));

            when(definitionRepository.findById(definition.getId()))
                    .thenReturn(Optional.of(definition));

            Definition foundDefinition = crudDefinitionService.findOne(concept, definition.getId());
            assertEquals(definition, foundDefinition);
        }

        @Test
        @DisplayName("Should not find a Definition with the given id in the database")
        void findOneWhenNotExists() {
            final Concept concept = new Concept(1L, "Software", new LinkedList<>());
            final long wrongDefinitionId = 99L;

            when(definitionRepository.findById(wrongDefinitionId))
                    .thenReturn(Optional.empty());

            assertThrows(DefinitionNotFoundException.class, () -> crudDefinitionService.findOne(concept, wrongDefinitionId));
        }

        @Test
        @DisplayName("Should not find a Definition with the given id on the given Concept")
        void findOneWhenNotBelongToConcept() {
            final Definition definition = new Definition(99L, "Hardware Definition", true, 3L);
            final Concept concept = new Concept(1L, "Software", new LinkedList<>());


            when(definitionRepository.findById(definition.getId()))
                    .thenReturn(Optional.of(definition));

            assertThrows(DefinitionNotBelongToConceptException.class, () -> crudDefinitionService.findOne(concept, definition.getId()));
        }
    }

    @Nested
    @DisplayName("FindAll")
    class DefinitionFindAll {

        @Test
        @DisplayName("Should find the list of Definitions in the given Concept")
        void findAllWhenDataExists() {
            final Definition definition = new Definition(2L, "Software Definition", true, 1L);
            final Concept concept = new Concept(1L, "Software", new LinkedList<>(List.of(definition)));

            List<Definition> definitionList = crudDefinitionService.findAll(concept);
            assertEquals(definitionList.size(), concept.getDefinitionList().size());
        }

        @Test
        @DisplayName("Should find an empty list of Definitions in the given Concept")
        void findAllWhenDataNotExists() {
            final Concept concept = new Concept(1L, "Software", new LinkedList<>());

            List<Definition> definitionList = crudDefinitionService.findAll(concept);
            assertEquals(definitionList.size(), 0);
        }
    }

    @Nested
    @DisplayName("UpdateOne")
    class DefinitionPut {

        @Test
        @DisplayName("Should update a Definition if exists")
        void updateWhenExists() {
            final Definition definition = new Definition(2L, "Software Definition", true, 1L);
            final Concept concept = new Concept(1L, "Software", new LinkedList<>(List.of(definition)));
            final DefinitionDTO definitionDTO = new DefinitionDTO("Software Definition", true);

            when(definitionRepository.findById(definition.getId()))
                    .thenReturn(Optional.of(definition));

            crudDefinitionService.updateOne(concept, definition.getId(), definitionDTO);
        }

        @Test
        @DisplayName("Should throw an Exception if the Definition is not found")
        void updateWhenNotExists() {
            final Concept concept = new Concept(1L, "Software", new LinkedList<>());
            final DefinitionDTO definitionDTO = new DefinitionDTO("Software Definition", true);
            final long wrongDefinitionId = 99L;

            assertThrows(DefinitionNotFoundException.class, () -> crudDefinitionService.updateOne(concept, wrongDefinitionId, definitionDTO));
        }
    }

    @Nested
    @DisplayName("RemoveOne")
    class DefinitionDelete {

        @Test
        @DisplayName("Should delete a Definition if exists")
        void deleteWhenExists() {
            final Definition definition = new Definition(2L, "Software Definition", true, 1L);
            final Concept concept = new Concept(1L, "Software", new LinkedList<>(List.of(definition)));


            when(definitionRepository.findById(definition.getId()))
                    .thenReturn(Optional.of(definition));

            crudDefinitionService.removeOne(concept, definition.getId());
        }

        @Test
        @DisplayName("Should throw an Exception if the Definition is not found")
        void deleteWhenNotExists() {
            final Concept concept = new Concept(1L, "Software", new LinkedList<>());
            final long wrongDefinitionId = 99L;

            assertThrows(DefinitionNotFoundException.class, () -> crudDefinitionService.removeOne(concept, wrongDefinitionId));
        }
    }

}