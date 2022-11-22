package com.example.backend.api.resources.exam.domain.factory.concrete;

import com.example.backend.api.resources.exam.domain.factory.TypeFactory;
import com.example.backend.api.resources.exam.domain.family.answer.tools.mapper.specific.AnswerT2Mapper;
import com.example.backend.api.resources.exam.domain.family.question.repository.QuestionT2Repository;
import com.example.backend.api.resources.exam.domain.family.question.tools.filler.QuestionFiller;
import com.example.backend.api.resources.exam.domain.family.question.tools.filler.specific.QuestionT2Filler;
import com.example.backend.api.resources.exam.domain.family.answer.tools.saver.AnswerSaver;
import com.example.backend.api.resources.exam.domain.family.answer.tools.saver.specific.AnswerT2Saver;
import com.example.backend.api.resources.exam.domain.family.answer.tools.mapper.AnswerMapper;
import com.example.backend.api.resources.exam.domain.family.answer.repository.AnswerT2Repository;
import com.example.backend.api.resources.exam.domain.family.question.tools.mapper.QuestionMapper;
import com.example.backend.api.resources.exam.domain.family.question.tools.mapper.specific.QuestionT2Mapper;
import com.example.backend.api.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.exam.domain.family.question.tools.generator.QuestionGenerator;
import com.example.backend.api.resources.exam.domain.family.question.tools.generator.specific.QuestionT2Generator;

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
    public QuestionGenerator createGenerator() {
        return new QuestionT2Generator(conceptRepository, definitionRepository, questionT2Repository);
    }

    @Override
    public long getNumberOfAvailableQuestions() {
        return definitionRepository.countAvailableType2Questions();
    }

    @Override
    public QuestionMapper createQuestionMapper() {
        return new QuestionT2Mapper(questionT2Repository);
    }

    @Override
    public AnswerMapper createAnswerMapper() {
        return new AnswerT2Mapper(answerT2Repository);
    }


    @Override
    public AnswerSaver getAnswerTypeService() {
        return new AnswerT2Saver(answerT2Repository);
    }

    @Override
    public QuestionFiller createQuestionFiller() {
        return new QuestionT2Filler(conceptRepository, definitionRepository);
    }
}
