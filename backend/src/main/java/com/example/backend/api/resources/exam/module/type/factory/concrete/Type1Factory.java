package com.example.backend.api.resources.exam.module.type.factory.concrete;

import com.example.backend.api.resources.exam.module.answer.mapper.AnswerMapper;
import com.example.backend.api.resources.exam.module.answer.mapper.specific.AnswerT0Mapper;
import com.example.backend.api.resources.exam.module.answer.mapper.specific.AnswerT1Mapper;
import com.example.backend.api.resources.exam.module.question.mapper.QuestionMapper;
import com.example.backend.api.resources.exam.module.question.mapper.specific.QuestionT1Mapper;
import com.example.backend.api.resources.exam.module.question.repository.QuestionT1Repository;
import com.example.backend.api.resources.exam.module.type.factory.TypeFactory;
import com.example.backend.api.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.exam.module.question.filler.QuestionFiller;
import com.example.backend.api.resources.exam.module.question.filler.specific.QuestionT1QuestionFiller;
import com.example.backend.api.resources.exam.module.question.model.Question;
import com.example.backend.api.resources.exam.module.question.model.specific.QuestionT1;
import com.example.backend.api.resources.exam.module.type.TypeData;

public class Type1Factory implements TypeFactory {


    private final ConceptRepository conceptRepository;

    private final DefinitionRepository definitionRepository;

    private final QuestionT1Repository questionT1Repository;


    public Type1Factory(
            ConceptRepository conceptRepository,
            DefinitionRepository definitionRepository,
            QuestionT1Repository questionT1Repository) {
        this.conceptRepository = conceptRepository;
        this.definitionRepository = definitionRepository;
        this.questionT1Repository = questionT1Repository;
    }

    @Override
    public Question createQuestion() {
        return new QuestionT1();
    }

    @Override
    public QuestionFiller createFiller() {
        return new QuestionT1QuestionFiller(conceptRepository, definitionRepository,questionT1Repository);
    }

    @Override
    public TypeData createNumberOfAvailableQuestions() {
        return new TypeData(definitionRepository.countAvailableType1Questions());
    }

    @Override
    public QuestionMapper createQuestionMapper() {
        return new QuestionT1Mapper(questionT1Repository);
    }

    @Override
    public AnswerMapper createAnswerMapper() {
        return new AnswerT1Mapper();
    }
}
