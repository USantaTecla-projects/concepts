package com.example.backend.unit.exam.question;


import com.example.backend.e2e.resources.exam.domain.factory.Type;
import com.example.backend.e2e.resources.exam.domain.family.question.model.Question;
import com.example.backend.e2e.resources.exam.domain.family.question.model.specific.QuestionT3;
import com.example.backend.e2e.resources.exam.domain.family.question.repository.QuestionT3Repository;
import com.example.backend.e2e.resources.exam.domain.family.question.tools.generator.specific.QuestionT3Generator;
import com.example.backend.e2e.resources.knowledge.concept.ConceptRepository;
import com.example.backend.e2e.resources.knowledge.concept.model.Concept;
import com.example.backend.e2e.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.e2e.resources.knowledge.definition.model.Definition;
import com.example.backend.e2e.resources.knowledge.justification.JustificationRepository;
import com.example.backend.e2e.resources.knowledge.justification.model.Justification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestionT3GeneratorTest {

    @Mock
    private ConceptRepository conceptRepository;

    @Mock
    private DefinitionRepository definitionRepository;

    @Mock
    private JustificationRepository justificationRepository;

    @Mock
    private QuestionT3Repository questionT3Repository;

    @InjectMocks
    private QuestionT3Generator questionT3Generator;

    @Test
    @DisplayName("Should get a question and create it on database")
    void newQuestion() {
        final Concept concept = new Concept(1L, "Software", new LinkedList<>());
        final Definition definition = new Definition(2L, "Software Definition", false, concept.getId(), new LinkedList<>());
        final Justification justification = new Justification(
                3L,
                "Software Justification",
                false,
                "Software Error",
                concept.getId(),
                definition.getId()
        );

        final List<Question> alreadyGeneratedQuestions = new LinkedList<>();

        when(definitionRepository.countDefinitionByIsCorrect(false)).thenReturn(1L);

        when(justificationRepository.findOneJustificationLinkedToIncorrectDefinition(anyList(), anyInt()))
                .thenReturn(Optional.of(justification));

        when(conceptRepository.findById(anyLong())).thenReturn(Optional.of(concept));
        when(definitionRepository.findById(anyLong())).thenReturn(Optional.of(definition));



        when(questionT3Repository.existsByConceptIDAndDefinitionIDAndJustificationID(anyLong(), anyLong(), anyLong())).thenReturn(false);
        when(questionT3Repository.save(any(QuestionT3.class))).thenReturn(
                new QuestionT3(
                        Type.TYPE3,
                        concept.getId(),
                        concept.getText(),
                        definition.getId(),
                        definition.getText(),
                        justification.getId(),
                        justification.getText()
                )
        );

        final Question question = questionT3Generator.generateQuestion(alreadyGeneratedQuestions);
        assert question instanceof QuestionT3;

        verify(questionT3Repository, never()).findByConceptIDAndDefinitionIDAndJustificationID(anyLong(), anyLong(), anyLong());
        verify(questionT3Repository).save(any(QuestionT3.class));
    }

    @Test
    @DisplayName("Should get a question that already exists on database")
    void existingQuestion() {
        final Concept concept = new Concept(1L, "Software", new LinkedList<>());
        final Definition definition = new Definition(2L, "Software Definition", false, concept.getId(), new LinkedList<>());
        final Justification justification = new Justification(
                3L,
                "Software Justification",
                false,
                "Software Error",
                concept.getId(),
                definition.getId()
        );

        final List<Question> alreadyGeneratedQuestions = new LinkedList<>();

        when(definitionRepository.countDefinitionByIsCorrect(false)).thenReturn(1L);

        when(justificationRepository.findOneJustificationLinkedToIncorrectDefinition(anyList(), anyInt()))
                .thenReturn(Optional.of(justification));

        when(conceptRepository.findById(anyLong())).thenReturn(Optional.of(concept));
        when(definitionRepository.findById(anyLong())).thenReturn(Optional.of(definition));



        when(questionT3Repository.existsByConceptIDAndDefinitionIDAndJustificationID(anyLong(), anyLong(), anyLong())).thenReturn(true);

        when(questionT3Repository.findByConceptIDAndDefinitionIDAndJustificationID(anyLong(), anyLong(), anyLong())).thenReturn(
                Optional.of(new QuestionT3(
                        Type.TYPE3,
                        concept.getId(),
                        concept.getText(),
                        definition.getId(),
                        definition.getText(),
                        justification.getId(),
                        justification.getText()
                ))
        );

        final Question question = questionT3Generator.generateQuestion(alreadyGeneratedQuestions);
        assert question instanceof QuestionT3;

        verify(questionT3Repository).findByConceptIDAndDefinitionIDAndJustificationID(anyLong(), anyLong(), anyLong());
        verify(questionT3Repository, never()).save(any(QuestionT3.class));
    }

    @Test
    @DisplayName("Should not return a question if the concept, definition and justification is already used in another")
    void repeatedConcept() {
        final Concept concept = new Concept(1L, "Software", new LinkedList<>());
        final Definition definition = new Definition(2L, "Software Definition", false, concept.getId(), new LinkedList<>());
        final Justification justification = new Justification(
                3L,
                "Software Justification",
                false,
                "Software Error",
                concept.getId(),
                definition.getId()
        );

        final List<Question> alreadyGeneratedQuestions = new LinkedList<>(
                List.of(new QuestionT3(
                        Type.TYPE3,
                        concept.getId(),
                        concept.getText(),
                        definition.getId(),
                        definition.getText(),
                        justification.getId(),
                        justification.getText()
                ))
        );

        when(definitionRepository.countDefinitionByIsCorrect(false)).thenReturn(1L);

        when(justificationRepository.findOneJustificationLinkedToIncorrectDefinition(anyList(), anyInt()))
                .thenReturn(Optional.empty());

        final Question question = questionT3Generator.generateQuestion(alreadyGeneratedQuestions);

        assert question == null;
    }
}
