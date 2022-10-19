package com.example.backend.api.resources.exam.domain.factory.concrete;

import com.example.backend.api.resources.exam.domain.family.answer.mapper.AnswerMapper;
import com.example.backend.api.resources.exam.domain.family.answer.mapper.specific.AnswerT1Mapper;
import com.example.backend.api.resources.exam.domain.family.answer.repository.AnswerT1Repository;
import com.example.backend.api.resources.exam.domain.family.answer.service.type.SaveAnswerTypeService;
import com.example.backend.api.resources.exam.domain.family.answer.service.type.specific.SaveAnswerT1Service;
import com.example.backend.api.resources.exam.domain.family.question.tools.mapper.QuestionMapper;
import com.example.backend.api.resources.exam.domain.family.question.tools.mapper.specific.QuestionT1Mapper;
import com.example.backend.api.resources.exam.domain.family.question.repository.QuestionT1Repository;
import com.example.backend.api.resources.exam.domain.family.question.service.saver.type.SaveQuestionTypeService;
import com.example.backend.api.resources.exam.domain.family.question.service.saver.type.specific.SaveQuestionT1Service;
import com.example.backend.api.resources.exam.domain.factory.TypeFactory;
import com.example.backend.api.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.exam.domain.family.question.tools.generator.QuestionGenerator;
import com.example.backend.api.resources.exam.domain.family.question.tools.generator.specific.QuestionT1Generator;

public class Type1Factory implements TypeFactory {

    private final ConceptRepository conceptRepository;
    private final DefinitionRepository definitionRepository;

    private final QuestionT1Repository questionT1Repository;
    private final AnswerT1Repository answerT1Repository;


    public Type1Factory(
            ConceptRepository conceptRepository,
            DefinitionRepository definitionRepository,
            QuestionT1Repository questionT1Repository,
            AnswerT1Repository answerT1Repository
    ) {
        this.conceptRepository = conceptRepository;
        this.definitionRepository = definitionRepository;
        this.questionT1Repository = questionT1Repository;
        this.answerT1Repository = answerT1Repository;
    }

    @Override
    public QuestionGenerator createFiller() {
        return new QuestionT1Generator(conceptRepository, definitionRepository, questionT1Repository);
    }

    @Override
    public long getNumberOfAvailableQuestions() {
        return definitionRepository.countAvailableType1Questions();
    }

    @Override
    public QuestionMapper createQuestionMapper() {
        return new QuestionT1Mapper(questionT1Repository);
    }

    @Override
    public AnswerMapper createAnswerMapper() {
        return new AnswerT1Mapper();
    }

    @Override
    public SaveQuestionTypeService getQuestionTypeService() {
        return new SaveQuestionT1Service(questionT1Repository);
    }

    @Override
    public SaveAnswerTypeService getAnswerTypeService() {
        return new SaveAnswerT1Service(answerT1Repository);
    }


}
