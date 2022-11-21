package com.example.backend.unit.exam.question;


import com.example.backend.e2e.resources.exam.domain.factory.Type;
import com.example.backend.e2e.resources.exam.domain.family.question.model.Question;
import com.example.backend.e2e.resources.exam.domain.family.question.model.specific.QuestionT1;
import com.example.backend.e2e.resources.exam.domain.family.question.repository.QuestionT1Repository;
import com.example.backend.e2e.resources.exam.domain.family.question.tools.generator.specific.QuestionT1Generator;
import com.example.backend.e2e.resources.knowledge.concept.ConceptRepository;
import com.example.backend.e2e.resources.knowledge.concept.model.Concept;
import com.example.backend.e2e.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.e2e.resources.knowledge.definition.model.Definition;
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
public class QuestionT1GeneratorTest {

    @Mock
    private ConceptRepository conceptRepository;

    @Mock
    private DefinitionRepository definitionRepository;

    @Mock
    private QuestionT1Repository questionT1Repository;

    @InjectMocks
    private QuestionT1Generator questionT1Generator;

    @Test
    @DisplayName("Should get a Question and create it on database")
    void newQuestion() {
        final Concept concept = new Concept(1L, "Software", new LinkedList<>());
        final Definition definition = new Definition(2L, "Incorrect Software Definition", false, concept.getId(), new LinkedList<>());
        final List<Question> alreadyGeneratedQuestions = new LinkedList<>();

        when(definitionRepository.countDefinitionByIsCorrect(false)).thenReturn(1L);
        when(definitionRepository.findRandomDefinitionBasedOnCorrect(anyList(), eq(false), anyInt()))
                .thenReturn(Optional.of(definition));

        when(conceptRepository.findById(anyLong())).thenReturn(Optional.of(concept));

        when(questionT1Repository.existsByConceptIDAndDefinitionID(1L, 2L)).thenReturn(false);
        when(questionT1Repository.save(any(QuestionT1.class)))
                .thenReturn(new QuestionT1(Type.TYPE1, concept.getId(), concept.getText(), definition.getId(), definition.getText()));

        final Question question = questionT1Generator.generateQuestion(alreadyGeneratedQuestions);
        assert question instanceof QuestionT1;

        verify(questionT1Repository, never()).findByConceptIDAndDefinitionID(anyLong(), anyLong());
        verify(questionT1Repository).save(any(QuestionT1.class));
    }

    @Test
    @DisplayName("Should get a Question that already exists on database")
    void existingQuestion() {
        final Concept concept = new Concept(1L, "Software", new LinkedList<>());
        final Definition definition = new Definition(2L, "Incorrect Software Definition", false, concept.getId(), new LinkedList<>());
        final List<Question> alreadyGeneratedQuestions = new LinkedList<>();

        when(definitionRepository.countDefinitionByIsCorrect(false)).thenReturn(1L);
        when(definitionRepository.findRandomDefinitionBasedOnCorrect(anyList(), eq(false), anyInt()))
                .thenReturn(Optional.of(definition));

        when(conceptRepository.findById(anyLong())).thenReturn(Optional.of(concept));

        when(questionT1Repository.existsByConceptIDAndDefinitionID(1L, 2L)).thenReturn(true);
        when(questionT1Repository.findByConceptIDAndDefinitionID(concept.getId(), definition.getId()))
                .thenReturn(Optional.of(new QuestionT1(Type.TYPE1, concept.getId(), concept.getText(), definition.getId(), definition.getText())));

        final Question question = questionT1Generator.generateQuestion(alreadyGeneratedQuestions);
        assert question instanceof QuestionT1;

        verify(questionT1Repository).findByConceptIDAndDefinitionID(anyLong(), anyLong());
        verify(questionT1Repository, never()).save(any(QuestionT1.class));
    }

    @Test
    @DisplayName("Should not return a question if the Definition is already used in another")
    void repeatedDefinition() {
        final Concept concept = new Concept(1L, "Software", new LinkedList<>());
        final Definition definition = new Definition(2L, "Incorrect Software Definition", false, concept.getId(), new LinkedList<>());
        final List<Question> alreadyGeneratedQuestions = new LinkedList<>(
                List.of(new QuestionT1(Type.TYPE1, concept.getId(), concept.getText(), definition.getId(), definition.getText()))
        );

        when(definitionRepository.countDefinitionByIsCorrect(false)).thenReturn(1L);
        when(definitionRepository.findRandomDefinitionBasedOnCorrect(anyList(), eq(false), anyInt()))
                .thenReturn(Optional.empty());

        final Question question = questionT1Generator.generateQuestion(alreadyGeneratedQuestions);
        assert question == null;
    }
}
