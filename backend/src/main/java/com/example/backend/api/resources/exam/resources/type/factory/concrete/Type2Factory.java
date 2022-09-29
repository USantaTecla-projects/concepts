package com.example.backend.api.resources.exam.resources.type.factory.concrete;

import com.example.backend.api.resources.exam.resources.question.repository.QuestionT2Repository;
import com.example.backend.api.resources.exam.resources.type.factory.TypeFactory;
import com.example.backend.api.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.exam.resources.question.filler.QuestionFiller;
import com.example.backend.api.resources.exam.resources.question.filler.specific.QuestionT2QuestionFiller;
import com.example.backend.api.resources.exam.resources.question.model.Question;
import com.example.backend.api.resources.exam.resources.question.model.specific.QuestionT2;
import com.example.backend.api.resources.exam.resources.type.TypeData;

public class Type2Factory implements TypeFactory {

    private final ConceptRepository conceptRepository;

    private final DefinitionRepository definitionRepository;

    private final QuestionT2Repository questionT2Repository;


    public Type2Factory(
            ConceptRepository conceptRepository,
            DefinitionRepository definitionRepository,
            QuestionT2Repository questionT2Repository
    ) {
        this.conceptRepository = conceptRepository;
        this.definitionRepository = definitionRepository;
        this.questionT2Repository = questionT2Repository;
    }

    @Override
    public Question createQuestion() {
        return new QuestionT2();
    }

    @Override
    public QuestionFiller createFiller() {
        return new QuestionT2QuestionFiller(conceptRepository, definitionRepository,questionT2Repository);
    }

    @Override
    public TypeData createNumberOfAvailableQuestions() {
        return new TypeData(definitionRepository.countAvailableType2Questions());
    }
}
