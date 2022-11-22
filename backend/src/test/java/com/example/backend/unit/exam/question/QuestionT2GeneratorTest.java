package com.example.backend.unit.exam.question;


import com.example.backend.api.resources.exam.domain.factory.Type;
import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.model.specific.QuestionT2;
import com.example.backend.api.resources.exam.domain.family.question.repository.QuestionT2Repository;
import com.example.backend.api.resources.exam.domain.family.question.tools.generator.specific.QuestionT2Generator;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import com.example.backend.api.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.api.resources.knowledge.definition.model.Definition;
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
public class QuestionT2GeneratorTest {
    @Mock
    private ConceptRepository conceptRepository;

    @Mock
    private DefinitionRepository definitionRepository;

    @Mock
    private QuestionT2Repository questionT2Repository;

    @InjectMocks
    private QuestionT2Generator questionT2Generator;

    @Test
    @DisplayName("Should get a question and create it on database")
    void newQuestion() {
        final Concept concept = new Concept(1L, "Software", new LinkedList<>());
        final Definition definition = new Definition(2L, "Software Definition", false, concept.getId(), new LinkedList<>());
        final List<Question> alreadyGeneratedQuestions = new LinkedList<>();

        when(definitionRepository.count()).thenReturn(1L);
        when(definitionRepository.findRandomDefinition(anyList(), anyInt())).thenReturn(Optional.of(definition));

        when(conceptRepository.findById(anyLong())).thenReturn(Optional.of(concept));

        when(questionT2Repository.existsByConceptIDAndDefinitionID(anyLong(), anyLong())).thenReturn(false);
        when(questionT2Repository.save(any(QuestionT2.class)))
                .thenReturn(new QuestionT2(Type.TYPE2, concept.getId(), concept.getText(), definition.getId(), definition.getText()));

        final Question question = questionT2Generator.generateQuestion(alreadyGeneratedQuestions);
        assert question instanceof QuestionT2;

        verify(questionT2Repository, never()).findByConceptIDAndDefinitionID(anyLong(), anyLong());
        verify(questionT2Repository).save(any(QuestionT2.class));
    }

    @Test
    @DisplayName("Should get a question that already exists on database")
    void existingQuestion() {
        final Concept concept = new Concept(1L, "Software", new LinkedList<>());
        final Definition definition = new Definition(2L, "Software Definition", false, concept.getId(), new LinkedList<>());
        final List<Question> alreadyGeneratedQuestions = new LinkedList<>();

        when(definitionRepository.count()).thenReturn(1L);
        when(definitionRepository.findRandomDefinition(anyList(), anyInt())).thenReturn(Optional.of(definition));

        when(conceptRepository.findById(anyLong())).thenReturn(Optional.of(concept));

        when(questionT2Repository.existsByConceptIDAndDefinitionID(anyLong(), anyLong())).thenReturn(true);
        when(questionT2Repository.findByConceptIDAndDefinitionID(anyLong(), anyLong()))
                .thenReturn(Optional.of(new QuestionT2(Type.TYPE2, concept.getId(), concept.getText(), definition.getId(), definition.getText())));

        final Question question = questionT2Generator.generateQuestion(alreadyGeneratedQuestions);
        assert question instanceof QuestionT2;

        verify(questionT2Repository).findByConceptIDAndDefinitionID(anyLong(), anyLong());
        verify(questionT2Repository, never()).save(any(QuestionT2.class));
    }

    @Test
    @DisplayName("Should not return a question if the concept and definition is already used in another")
    void repeatedConcept() {
        final Concept concept = new Concept(1L, "Software", new LinkedList<>());
        final Definition definition = new Definition(2L, "Software Definition", false, concept.getId(), new LinkedList<>());
        final List<Question> alreadyGeneratedQuestions = new LinkedList<>(
                List.of(new QuestionT2(Type.TYPE2, concept.getId(), concept.getText(), definition.getId(), definition.getText()))
        );

        when(definitionRepository.count()).thenReturn(1L);
        when(definitionRepository.findRandomDefinition(anyList(), anyInt())).thenReturn(Optional.empty());

        final Question question = questionT2Generator.generateQuestion(alreadyGeneratedQuestions);

        assert question == null;
    }
}
