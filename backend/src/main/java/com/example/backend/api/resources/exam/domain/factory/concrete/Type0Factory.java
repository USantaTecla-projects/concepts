package com.example.backend.api.resources.exam.domain.factory.concrete;

import com.example.backend.api.resources.exam.domain.family.answer.mapper.AnswerMapper;
import com.example.backend.api.resources.exam.domain.family.answer.mapper.specific.AnswerT0Mapper;
import com.example.backend.api.resources.exam.domain.family.answer.repository.AnswerT0Repository;
import com.example.backend.api.resources.exam.domain.family.answer.service.type.SaveAnswerTypeService;
import com.example.backend.api.resources.exam.domain.family.answer.service.type.specific.SaveAnswerT0Service;
import com.example.backend.api.resources.exam.domain.family.question.tools.filler.QuestionFiller;
import com.example.backend.api.resources.exam.domain.family.question.tools.filler.specific.QuestionT0Filler;
import com.example.backend.api.resources.exam.domain.family.question.tools.mapper.QuestionMapper;
import com.example.backend.api.resources.exam.domain.family.question.tools.mapper.specific.QuestionT0Mapper;
import com.example.backend.api.resources.exam.domain.family.question.repository.QuestionT0Repository;
import com.example.backend.api.resources.exam.domain.family.question.service.saver.type.SaveQuestionTypeService;
import com.example.backend.api.resources.exam.domain.family.question.service.saver.type.specific.SaveQuestionT0Service;
import com.example.backend.api.resources.exam.domain.factory.TypeFactory;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.exam.domain.family.question.tools.generator.QuestionGenerator;
import com.example.backend.api.resources.exam.domain.family.question.tools.generator.specific.QuestionT0Generator;

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
    public QuestionGenerator createGenerator() {
        return new QuestionT0Generator(conceptRepository, questionT0Repository);
    }

    @Override
    public long getNumberOfAvailableQuestions() {
        return conceptRepository.countAvailableType0Questions();
    }

    @Override
    public QuestionMapper createQuestionMapper() {
        return new QuestionT0Mapper(questionT0Repository);
    }

    @Override
    public AnswerMapper createAnswerMapper() {
        return new AnswerT0Mapper(answerT0Repository);
    }

    @Override
    public SaveAnswerTypeService getAnswerTypeService() {
        return new SaveAnswerT0Service(answerT0Repository);
    }

    @Override
    public SaveQuestionTypeService getQuestionTypeService() {
        return new SaveQuestionT0Service(questionT0Repository);
    }

    @Override
    public QuestionFiller createQuestionFiller() {
        return new QuestionT0Filler(conceptRepository);
    }
}
