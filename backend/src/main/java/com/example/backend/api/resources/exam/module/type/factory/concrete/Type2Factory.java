package com.example.backend.api.resources.exam.module.type.factory.concrete;

import com.example.backend.api.resources.exam.module.answer.mapper.AnswerMapper;
import com.example.backend.api.resources.exam.module.answer.mapper.specific.AnswerT2Mapper;
import com.example.backend.api.resources.exam.module.answer.repository.AnswerT2Repository;
import com.example.backend.api.resources.exam.module.answer.service.type.AnswerTypeService;
import com.example.backend.api.resources.exam.module.answer.service.type.specific.AnswerT2Service;
import com.example.backend.api.resources.exam.module.question.mapper.QuestionMapper;
import com.example.backend.api.resources.exam.module.question.mapper.specific.QuestionT2Mapper;
import com.example.backend.api.resources.exam.module.question.repository.QuestionT2Repository;
import com.example.backend.api.resources.exam.module.question.service.type.QuestionTypeService;
import com.example.backend.api.resources.exam.module.question.service.type.specific.QuestionT2Service;
import com.example.backend.api.resources.exam.module.type.factory.TypeFactory;
import com.example.backend.api.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.exam.module.question.generator.QuestionGenerator;
import com.example.backend.api.resources.exam.module.question.generator.specific.QuestionT2Generator;
import com.example.backend.api.resources.exam.module.question.model.Question;
import com.example.backend.api.resources.exam.module.question.model.specific.QuestionT2;
import com.example.backend.api.resources.exam.module.type.TypeData;

public class Type2Factory implements TypeFactory {

    private final ConceptRepository conceptRepository;
    private final DefinitionRepository definitionRepository;

    private final QuestionT2Repository questionT2Repository;
    private final AnswerT2Repository answerT2Repository;


    public Type2Factory(
            ConceptRepository conceptRepository,
            DefinitionRepository definitionRepository,
            QuestionT2Repository questionT2Repository,
            AnswerT2Repository answerT2Repository
    ) {
        this.conceptRepository = conceptRepository;
        this.definitionRepository = definitionRepository;
        this.questionT2Repository = questionT2Repository;
        this.answerT2Repository = answerT2Repository;
    }

    @Override
    public Question createQuestion() {
        return new QuestionT2();
    }

    @Override
    public QuestionGenerator createFiller() {
        return new QuestionT2Generator(conceptRepository, definitionRepository, questionT2Repository);
    }

    @Override
    public TypeData createNumberOfAvailableQuestions() {
        return new TypeData(definitionRepository.countAvailableType2Questions());
    }

    @Override
    public QuestionMapper createQuestionMapper() {
        return new QuestionT2Mapper(questionT2Repository);
    }

    @Override
    public AnswerMapper createAnswerMapper() {
        return new AnswerT2Mapper();
    }

    @Override
    public QuestionTypeService getQuestionTypeService() {
        return new QuestionT2Service(questionT2Repository);
    }

    @Override
    public AnswerTypeService getAnswerTypeService() {
        return new AnswerT2Service(answerT2Repository);
    }


}
