package com.example.backend.unit.exam.question.generator;


import com.example.backend.e2e.resources.exam.domain.family.question.model.Question;
import com.example.backend.e2e.resources.exam.domain.family.question.model.specific.QuestionT0;
import com.example.backend.e2e.resources.exam.domain.family.question.model.specific.QuestionT2;
import com.example.backend.e2e.resources.exam.domain.family.question.repository.QuestionT0Repository;
import com.example.backend.e2e.resources.exam.domain.family.question.repository.QuestionT1Repository;
import com.example.backend.e2e.resources.exam.domain.family.question.repository.QuestionT2Repository;
import com.example.backend.e2e.resources.exam.domain.family.question.tools.generator.specific.QuestionT0Generator;
import com.example.backend.e2e.resources.exam.domain.family.question.tools.generator.specific.QuestionT1Generator;
import com.example.backend.e2e.resources.exam.domain.family.question.tools.generator.specific.QuestionT2Generator;
import com.example.backend.e2e.resources.knowledge.concept.ConceptRepository;
import com.example.backend.e2e.resources.knowledge.concept.model.Concept;
import com.example.backend.e2e.resources.knowledge.definition.DefinitionRepository;
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

    }

    @Test
    @DisplayName("Should get a question that already exists on database")
    void existingQuestion() {

    }

    @Test
    @DisplayName("Should not return a question if the concept and definition is already used in another")
    void repeatedConcept() {

    }
}
