package com.example.backend.api.resources.exam.domain.factory.concrete;

import com.example.backend.api.resources.exam.domain.family.answer.mapper.AnswerMapper;
import com.example.backend.api.resources.exam.domain.family.answer.mapper.specific.AnswerT3Mapper;
import com.example.backend.api.resources.exam.domain.family.answer.repository.AnswerT3Repository;
import com.example.backend.api.resources.exam.domain.family.answer.service.type.SaveAnswerTypeService;
import com.example.backend.api.resources.exam.domain.family.answer.service.type.specific.SaveAnswerT3Service;
import com.example.backend.api.resources.exam.domain.family.question.tools.mapper.QuestionMapper;
import com.example.backend.api.resources.exam.domain.family.question.tools.mapper.specific.QuestionT3Mapper;
import com.example.backend.api.resources.exam.domain.family.question.repository.QuestionT3Repository;
import com.example.backend.api.resources.exam.domain.family.question.service.saver.type.SaveQuestionTypeService;
import com.example.backend.api.resources.exam.domain.family.question.service.saver.type.specific.SaveQuestionT3Service;
import com.example.backend.api.resources.exam.domain.factory.TypeFactory;
import com.example.backend.api.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.justification.JustificationRepository;
import com.example.backend.api.resources.exam.domain.family.question.tools.generator.QuestionGenerator;
import com.example.backend.api.resources.exam.domain.family.question.tools.generator.specific.QuestionT3Generator;

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
    public QuestionGenerator createFiller() {
        return new QuestionT3Generator(conceptRepository, definitionRepository, justificationRepository, questionT3Repository);
    }

    @Override
    public long getNumberOfAvailableQuestions() {
        return justificationRepository.countAvailableType3Questions();
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
    public SaveQuestionTypeService getQuestionTypeService() {
        return new SaveQuestionT3Service(questionT3Repository);
    }

    @Override
    public SaveAnswerTypeService getAnswerTypeService() {
        return new SaveAnswerT3Service(answerT3Repository);
    }


}
