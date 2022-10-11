package com.example.backend.api.resources.exam.module.type.factory.concrete;

import com.example.backend.api.resources.exam.module.answer.mapper.AnswerMapper;
import com.example.backend.api.resources.exam.module.answer.mapper.specific.AnswerT0Mapper;
import com.example.backend.api.resources.exam.module.answer.repository.AnswerT0Repository;
import com.example.backend.api.resources.exam.module.answer.service.type.AnswerTypeService;
import com.example.backend.api.resources.exam.module.answer.service.type.specific.AnswerT0Service;
import com.example.backend.api.resources.exam.module.question.mapper.QuestionMapper;
import com.example.backend.api.resources.exam.module.question.mapper.specific.QuestionT0Mapper;
import com.example.backend.api.resources.exam.module.question.repository.QuestionT0Repository;
import com.example.backend.api.resources.exam.module.question.service.type.QuestionTypeService;
import com.example.backend.api.resources.exam.module.question.service.type.specific.QuestionT0Service;
import com.example.backend.api.resources.exam.module.type.factory.TypeFactory;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.exam.module.question.generator.QuestionGenerator;
import com.example.backend.api.resources.exam.module.question.generator.specific.QuestionT0Generator;
import com.example.backend.api.resources.exam.module.question.model.Question;
import com.example.backend.api.resources.exam.module.question.model.specific.QuestionT0;
import com.example.backend.api.resources.exam.module.type.TypeData;

public class Type0Factory implements TypeFactory {

    private final ConceptRepository conceptRepository;

    private final QuestionT0Repository questionT0Repository;
    private final AnswerT0Repository answerT0Repository;


    public Type0Factory(
            ConceptRepository conceptRepository,
            QuestionT0Repository questionT0Repository,
            AnswerT0Repository answerT0Repository
    ) {
        this.conceptRepository = conceptRepository;
        this.questionT0Repository = questionT0Repository;
        this.answerT0Repository = answerT0Repository;
    }

    @Override
    public Question createQuestion() {
        return new QuestionT0();
    }

    @Override
    public QuestionGenerator createFiller() {
        return new QuestionT0Generator(conceptRepository, questionT0Repository);
    }

    @Override
    public TypeData createNumberOfAvailableQuestions() {
        return new TypeData(conceptRepository.countAvailableType0Questions());
    }

    @Override
    public QuestionMapper createQuestionMapper() {
        return new QuestionT0Mapper(questionT0Repository);
    }

    @Override
    public AnswerMapper createAnswerMapper() {
        return new AnswerT0Mapper();
    }

    @Override
    public QuestionTypeService getQuestionTypeService() {
        return new QuestionT0Service(questionT0Repository);
    }

    @Override
    public AnswerTypeService getAnswerTypeService() {
        return new AnswerT0Service(answerT0Repository);
    }


}
