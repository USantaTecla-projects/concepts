package com.example.backend.api.core.answer;

import com.example.backend.api.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.api.resources.knowledge.definition.DefinitionService;
import com.example.backend.api.resources.knowledge.definition.dto.DefinitionDTO;
import com.example.backend.api.resources.knowledge.definition.exception.specific.DefinitionDTOBadRequestException;
import com.example.backend.api.resources.knowledge.definition.exception.specific.DefinitionNotBelongToConceptException;
import com.example.backend.api.resources.knowledge.definition.exception.specific.DefinitionNotFoundException;
import com.example.backend.api.resources.knowledge.definition.model.Definition;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
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
public class DefinitionServiceTest {

    @Mock
    private ConceptRepository conceptRepository;

    @Mock
    private DefinitionRepository definitionRepository;

    @InjectMocks
    private DefinitionService definitionService;


    @Nested
    @DisplayName("POST")
    class DefinitionPost {

        @Test
        @DisplayName("(Create) Should create an Answer with a correct DTO")
        void createWithCorrectDTO() {
            final DefinitionDTO definitionDTO = new DefinitionDTO("Software answer", true);
            final Definition definition = new Definition(2L, "Software answer", true, 1L);
            final Concept concept = new Concept(1L, "Software", new LinkedList<>(List.of(definition)));

            when(definitionRepository.save(any(Definition.class)))
                    .thenReturn(definition);

            Definition createdDefinition = definitionService.create(concept, definitionDTO);


            // The Answer is created correctly
            assertEquals(definition.getText(), createdDefinition.getText());
            assertEquals(definition.getConceptID(), createdDefinition.getConceptID());

            // The Answer is added to the Concept
            assertEquals(
                    concept.getDefinitionList().get(0).getText(),
                    definition.getText()
            );
            assertEquals(concept.getDefinitionList().get(0).getConceptID(), concept.getId());
        }

        @Test
        @DisplayName("(Create) Should not create an Answer with a wrong DTO")
        void createWithWrongDTO() {
            final DefinitionDTO wrongDefinitionDTO = new DefinitionDTO();
            final Definition definition = new Definition(2L, "Software answer", true, 1L);
            final Concept concept = new Concept(1L, "Software", new LinkedList<>(List.of(definition)));

            assertThrows(DefinitionDTOBadRequestException.class, () -> definitionService.create(concept, wrongDefinitionDTO));
        }
    }

    @Nested
    @DisplayName("GET")
    class DefinitionGet {

        @Test
        @DisplayName("(FindOne) Should find an Answer with the given id")
        void findOneWhenExists() {
            final Definition definition = new Definition(2L, "Software answer", true, 1L);
            final Concept concept = new Concept(1L, "Software", new LinkedList<>(List.of(definition)));

            when(definitionRepository.findById(definition.getId()))
                    .thenReturn(Optional.of(definition));

            Definition foundDefinition = definitionService.findOne(concept, definition.getId());
            assertEquals(definition, foundDefinition);
        }

        @Test
        @DisplayName("(FindOne) Should not find an Answer with the given id in the database")
        void findOneWhenNotExists() {
            final Concept concept = new Concept(1L, "Software", new LinkedList<>());
            final long wrongAnswerId = 99L;

            when(definitionRepository.findById(wrongAnswerId))
                    .thenReturn(Optional.empty());

            assertThrows(DefinitionNotFoundException.class, () -> definitionService.findOne(concept, wrongAnswerId));
        }

        @Test
        @DisplayName("(FindOne) Should not find an Answer with the given id on the given Concept")
        void findOneWhenNotBelongToConcept() {
            final Definition definition = new Definition(99L, "Hardware answer", true, 3L);
            final Concept concept = new Concept(1L, "Software", new LinkedList<>());


            when(definitionRepository.findById(definition.getId()))
                    .thenReturn(Optional.of(definition));

            assertThrows(DefinitionNotBelongToConceptException.class, () -> definitionService.findOne(concept, definition.getId()));
        }

        @Test
        @DisplayName("(FindAll) Should find the list of answers in the given Concept")
        void findAllWhenDataExists() {
            final Definition definition = new Definition(2L, "Software answer", true, 1L);
            final Concept concept = new Concept(1L, "Software", new LinkedList<>(List.of(definition)));

            List<Definition> definitionList = definitionService.findAll(concept);
            assertEquals(definitionList.size(), concept.getDefinitionList().size());
        }

        @Test
        @DisplayName("(FindAll) Should find an empty list of answers in the given Concept")
        void findAllWhenDataNotExists() {
            final Concept concept = new Concept(1L, "Software", new LinkedList<>());

            List<Definition> definitionList = definitionService.findAll(concept);
            assertEquals(definitionList.size(), 0);
        }

    }

    @Nested
    @DisplayName("PUT")
    class DefinitionPut {

        @Test
        @DisplayName("(Update) Should update an Answer if exists")
        void updateWhenExists() {
            final Definition definition = new Definition(2L, "Software answer", true, 1L);
            final Concept concept = new Concept(1L, "Software", new LinkedList<>(List.of(definition)));
            final DefinitionDTO definitionDTO = new DefinitionDTO("Software answer", true);

            when(definitionRepository.findById(definition.getId()))
                    .thenReturn(Optional.of(definition));

            definitionService.updateOne(concept, definition.getId(), definitionDTO);
        }

        @Test
        @DisplayName("(Update) Should throw an exception if the Answer is not found")
        void updateWhenNotExists() {
            final Concept concept = new Concept(1L, "Software", new LinkedList<>());
            final DefinitionDTO definitionDTO = new DefinitionDTO("Software answer", true);
            final long wrongAnswerId = 99L;

            assertThrows(DefinitionNotFoundException.class, () -> definitionService.updateOne(concept, wrongAnswerId, definitionDTO));
        }
    }

    @Nested
    @DisplayName("DELETE")
    class DefinitionDelete {

        @Test
        @DisplayName("(Delete) Should delete an Answer if exists")
        void deleteWhenExists() {
            final Definition definition = new Definition(2L, "Software answer", true, 1L);
            final Concept concept = new Concept(1L, "Software", new LinkedList<>(List.of(definition)));


            when(definitionRepository.findById(definition.getId()))
                    .thenReturn(Optional.of(definition));

            definitionService.removeOne(concept, definition.getId());
        }

        @Test
        @DisplayName("(Delete) Should throw an exception if the Answer is not found")
        void deleteWhenNotExists() {
            final Concept concept = new Concept(1L, "Software", new LinkedList<>());
            final long wrongAnswerId = 99L;

            assertThrows(DefinitionNotFoundException.class, () -> definitionService.removeOne(concept, wrongAnswerId));
        }
    }

}
