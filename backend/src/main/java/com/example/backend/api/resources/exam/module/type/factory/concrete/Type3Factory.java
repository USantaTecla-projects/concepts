package com.example.backend.api.resources.exam.module.type.factory.concrete;

import com.example.backend.api.resources.exam.module.answer.mapper.AnswerMapper;
import com.example.backend.api.resources.exam.module.answer.mapper.specific.AnswerT3Mapper;
import com.example.backend.api.resources.exam.module.answer.repository.AnswerT3Repository;
import com.example.backend.api.resources.exam.module.answer.service.type.AnswerTypeService;
import com.example.backend.api.resources.exam.module.answer.service.type.specific.AnswerT3Service;
import com.example.backend.api.resources.exam.module.question.mapper.QuestionMapper;
import com.example.backend.api.resources.exam.module.question.mapper.specific.QuestionT3Mapper;
import com.example.backend.api.resources.exam.module.question.repository.QuestionT3Repository;
import com.example.backend.api.resources.exam.module.question.service.type.QuestionTypeService;
import com.example.backend.api.resources.exam.module.question.service.type.specific.QuestionT3Service;
import com.example.backend.api.resources.exam.module.type.factory.TypeFactory;
import com.example.backend.api.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.justification.JustificationRepository;
import com.example.backend.api.resources.exam.module.question.filler.QuestionFiller;
import com.example.backend.api.resources.exam.module.question.filler.specific.QuestionT3Filler;
import com.example.backend.api.resources.exam.module.question.model.Question;
import com.example.backend.api.resources.exam.module.question.model.specific.QuestionT3;
import com.example.backend.api.resources.exam.module.type.TypeData;

public class Type3Factory implements TypeFactory {

    private final ConceptRepository conceptRepository;
    private final DefinitionRepository definitionRepository;
    private final JustificationRepository justificationRepository;

    private final QuestionT3Repository questionT3Repository;
    private final AnswerT3Repository answerT3Repository;

    public Type3Factory(
            ConceptRepository conceptRepository,
            DefinitionRepository definitionRepository,
            JustificationRepository justificationRepository,
            QuestionT3Repository questionT3Repository,
            AnswerT3Repository answerT3Repository) {
        this.conceptRepository = conceptRepository;
        this.definitionRepository = definitionRepository;
        this.justificationRepository = justificationRepository;
        this.questionT3Repository = questionT3Repository;
        this.answerT3Repository = answerT3Repository;
    }

    @Override
    public Question createQuestion() {
        return new QuestionT3();
    }

    @Override
    public QuestionFiller createFiller() {
        return new QuestionT3Filler(conceptRepository, definitionRepository, justificationRepository, questionT3Repository);
    }

    @Override
    public TypeData createNumberOfAvailableQuestions() {
        return new TypeData(justificationRepository.countAvailableType3Questions());
    }

    @Override
    public QuestionMapper createQuestionMapper() {
        return new QuestionT3Mapper(questionT3Repository);
    }

    @Override
    public AnswerMapper createAnswerMapper() {
        return new AnswerT3Mapper();
    }

    @Override
    public QuestionTypeService getQuestionTypeService() {
        return new QuestionT3Service(questionT3Repository);
    }

    @Override
    public AnswerTypeService getAnswerTypeService() {
        return new AnswerT3Service(answerT3Repository);
    }


}
