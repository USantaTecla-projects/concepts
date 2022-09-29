package com.example.backend.api.resources.exam.resources.type.factory.concrete;

import com.example.backend.api.resources.exam.resources.question.repository.QuestionT3Repository;
import com.example.backend.api.resources.exam.resources.type.factory.TypeFactory;
import com.example.backend.api.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.justification.JustificationRepository;
import com.example.backend.api.resources.exam.resources.question.filler.QuestionFiller;
import com.example.backend.api.resources.exam.resources.question.filler.specific.QuestionT3QuestionFiller;
import com.example.backend.api.resources.exam.resources.question.model.Question;
import com.example.backend.api.resources.exam.resources.question.model.specific.QuestionT3;
import com.example.backend.api.resources.exam.resources.type.TypeData;

public class Type3Factory implements TypeFactory {

    private final ConceptRepository conceptRepository;

    private final DefinitionRepository definitionRepository;

    private final JustificationRepository justificationRepository;

    private final QuestionT3Repository questionT3Repository;


    public Type3Factory(
            ConceptRepository conceptRepository,
            DefinitionRepository definitionRepository,
            JustificationRepository justificationRepository,
            QuestionT3Repository questionT3Repository
    ) {
        this.conceptRepository = conceptRepository;
        this.definitionRepository = definitionRepository;
        this.justificationRepository = justificationRepository;
        this.questionT3Repository = questionT3Repository;
    }

    @Override
    public Question createQuestion() {
        return new QuestionT3();
    }

    @Override
    public QuestionFiller createFiller() {
        return new QuestionT3QuestionFiller(conceptRepository, definitionRepository, justificationRepository,questionT3Repository);
    }

    @Override
    public TypeData createNumberOfAvailableQuestions() {
        return new TypeData(justificationRepository.countAvailableType3Questions());
    }
}
