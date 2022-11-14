package com.example.backend.api.resources.exam.domain.factory.concrete;

import com.example.backend.api.resources.exam.domain.family.answer.tools.saver.type.AnswerTypeSaver;
import com.example.backend.api.resources.exam.domain.family.answer.tools.saver.type.specific.AnswerT3Saver;
import com.example.backend.api.resources.exam.domain.family.answer.tools.mapper.AnswerMapper;
import com.example.backend.api.resources.exam.domain.family.answer.tools.mapper.specific.AnswerT3Mapper;
import com.example.backend.api.resources.exam.domain.family.answer.repository.AnswerT3Repository;
import com.example.backend.api.resources.exam.domain.family.question.tools.filler.QuestionFiller;
import com.example.backend.api.resources.exam.domain.family.question.tools.filler.specific.QuestionT3Filler;
import com.example.backend.api.resources.exam.domain.family.question.tools.mapper.QuestionMapper;
import com.example.backend.api.resources.exam.domain.family.question.tools.mapper.specific.QuestionT3Mapper;
import com.example.backend.api.resources.exam.domain.family.question.repository.QuestionT3Repository;
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
    public QuestionGenerator createGenerator() {
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
        return new AnswerT3Mapper(answerT3Repository);
    }


    @Override
    public AnswerTypeSaver getAnswerTypeService() {
        return new AnswerT3Saver(answerT3Repository);
    }

    @Override
    public QuestionFiller createQuestionFiller() {
        return new QuestionT3Filler(conceptRepository, definitionRepository, justificationRepository);
    }
}
