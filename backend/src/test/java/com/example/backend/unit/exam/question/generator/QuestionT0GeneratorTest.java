package com.example.backend.unit.exam.question.generator;


import com.example.backend.e2e.resources.exam.domain.family.question.model.Question;
import com.example.backend.e2e.resources.exam.domain.family.question.model.specific.QuestionT0;
import com.example.backend.e2e.resources.exam.domain.family.question.repository.QuestionT0Repository;
import com.example.backend.e2e.resources.exam.domain.family.question.tools.generator.specific.QuestionT0Generator;
import com.example.backend.e2e.resources.knowledge.concept.ConceptRepository;
import com.example.backend.e2e.resources.knowledge.concept.model.Concept;
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
public class QuestionT0GeneratorTest {

    @Mock
    private ConceptRepository conceptRepository;

    @Mock
    private QuestionT0Repository questionT0Repository;

    @InjectMocks
    private QuestionT0Generator questionT0Generator;

    @Test
    @DisplayName("Should get a question and create it on database")
    void newQuestion() {
        final Concept concept = new Concept(1L, "Software", new LinkedList<>());
        List<Question> alreadyGeneratedQuestions = new LinkedList<>();

        when(conceptRepository.count()).thenReturn(1L);
        when(conceptRepository.findRandomConcept(anyList(), anyInt()))
                .thenReturn(Optional.of(concept));

        when(questionT0Repository.existsByConceptID(1L)).thenReturn(false);
        when(questionT0Repository.save(any(QuestionT0.class)))
                .thenReturn(new QuestionT0(concept.getId(), concept.getText()));

        Question question = questionT0Generator.generateQuestion(alreadyGeneratedQuestions);

        assert question != null;
        verify(questionT0Repository, never()).findByConceptID(anyLong());
        verify(questionT0Repository).save(any(QuestionT0.class));
    }

    @Test
    @DisplayName("Should get a question that already exists on database")
    void existingQuestion() {
        final Concept concept = new Concept(1L, "Software", new LinkedList<>());
        List<Question> alreadyGeneratedQuestions = new LinkedList<>();

        when(conceptRepository.count()).thenReturn(1L);
        when(conceptRepository.findRandomConcept(anyList(), anyInt()))
                .thenReturn(Optional.of(concept));

        when(questionT0Repository.existsByConceptID(1L)).thenReturn(true);
        when(questionT0Repository.findByConceptID(anyLong()))
                .thenReturn(Optional.of(new QuestionT0(concept.getId(), concept.getText())));

        Question question = questionT0Generator.generateQuestion(alreadyGeneratedQuestions);

        assert question != null;
        verify(questionT0Repository).findByConceptID(anyLong());
        verify(questionT0Repository, never()).save(any(QuestionT0.class));
    }

    @Test
    @DisplayName("Should not return a question if the concept is already used in another")
    void repeatedConcept() {
        final Concept concept = new Concept(1L, "Software", new LinkedList<>());
        List<Question> alreadyGeneratedQuestions = new LinkedList<>(List.of(new QuestionT0(concept.getId(), concept.getText())));

        when(conceptRepository.count()).thenReturn(1L);
        when(conceptRepository.findRandomConcept(anyList(), anyInt()))
                .thenReturn(Optional.empty());

        Question question = questionT0Generator.generateQuestion(alreadyGeneratedQuestions);

        assert question == null;
    }


}
