package com.example.backend.api.resources.exam.module.type.factory.concrete;

import com.example.backend.api.resources.exam.module.answer.mapper.AnswerMapper;
import com.example.backend.api.resources.exam.module.answer.mapper.specific.AnswerT0Mapper;
import com.example.backend.api.resources.exam.module.question.mapper.QuestionMapper;
import com.example.backend.api.resources.exam.module.question.mapper.specific.QuestionT0Mapper;
import com.example.backend.api.resources.exam.module.question.repository.QuestionT0Repository;
import com.example.backend.api.resources.exam.module.type.factory.TypeFactory;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.exam.module.question.filler.QuestionFiller;
import com.example.backend.api.resources.exam.module.question.filler.specific.QuestionT0QuestionFiller;
import com.example.backend.api.resources.exam.module.question.model.Question;
import com.example.backend.api.resources.exam.module.question.model.specific.QuestionT0;
import com.example.backend.api.resources.exam.module.type.TypeData;

public class Type0Factory implements TypeFactory {

    private final ConceptRepository conceptRepository;

    private final QuestionT0Repository questionT0Repository;

    public Type0Factory(ConceptRepository conceptRepository, QuestionT0Repository questionT0Repository) {
        this.conceptRepository = conceptRepository;
        this.questionT0Repository = questionT0Repository;
    }

    @Override
    public Question createQuestion() {
        return new QuestionT0();
    }

    @Override
    public QuestionFiller createFiller() {
        return new QuestionT0QuestionFiller(conceptRepository, questionT0Repository);
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
}
