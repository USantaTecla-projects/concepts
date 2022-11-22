package com.example.backend.api.resources.exam.domain.factory;

import com.example.backend.api.resources.exam.domain.family.answer.repository.AnswerT3Repository;
import com.example.backend.api.resources.exam.domain.family.question.repository.QuestionT0Repository;
import com.example.backend.api.resources.exam.domain.family.question.repository.QuestionT1Repository;
import com.example.backend.api.resources.exam.domain.family.question.repository.QuestionT2Repository;
import com.example.backend.api.resources.exam.domain.family.answer.repository.AnswerT0Repository;
import com.example.backend.api.resources.exam.domain.family.answer.repository.AnswerT1Repository;
import com.example.backend.api.resources.exam.domain.family.answer.repository.AnswerT2Repository;
import com.example.backend.api.resources.exam.domain.family.question.repository.QuestionT3Repository;
import com.example.backend.api.resources.exam.domain.factory.concrete.Type3Factory;
import com.example.backend.api.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.justification.JustificationRepository;
import com.example.backend.api.resources.exam.domain.factory.concrete.Type0Factory;
import com.example.backend.api.resources.exam.domain.factory.concrete.Type1Factory;
import com.example.backend.api.resources.exam.domain.factory.concrete.Type2Factory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeFactoryProvider {

    private final ConceptRepository conceptRepository;
    private final DefinitionRepository definitionRepository;
    private final JustificationRepository justificationRepository;

    private final QuestionT0Repository questionT0Repository;
    private final QuestionT1Repository questionT1Repository;
    private final QuestionT2Repository questionT2Repository;
    private final QuestionT3Repository questionT3Repository;

    private final AnswerT0Repository answerT0Repository;
    private final AnswerT1Repository answerT1Repository;
    private final AnswerT2Repository answerT2Repository;
    private final AnswerT3Repository answerT3Repository;

    public TypeFactoryProvider(
            ConceptRepository conceptRepository,
            DefinitionRepository definitionRepository,
            JustificationRepository justificationRepository,
            QuestionT0Repository questionT0Repository,
            QuestionT1Repository questionT1Repository,
            QuestionT2Repository questionT2Repository,
            QuestionT3Repository questionT3Repository,
            AnswerT0Repository answerT0Repository,
            AnswerT1Repository answerT1Repository,
            AnswerT2Repository answerT2Repository,
            AnswerT3Repository answerT3Repository
    ) {
        this.conceptRepository = conceptRepository;
        this.definitionRepository = definitionRepository;
        this.justificationRepository = justificationRepository;
        this.questionT0Repository = questionT0Repository;
        this.questionT1Repository = questionT1Repository;
        this.questionT2Repository = questionT2Repository;
        this.questionT3Repository = questionT3Repository;
        this.answerT0Repository = answerT0Repository;
        this.answerT1Repository = answerT1Repository;
        this.answerT2Repository = answerT2Repository;
        this.answerT3Repository = answerT3Repository;
    }

    public List<TypeFactory> getTypeAbstractFactories() {
        return List.of(
                new Type0Factory(conceptRepository, questionT0Repository, answerT0Repository),
                new Type1Factory(conceptRepository, definitionRepository, questionT1Repository, answerT1Repository),
                new Type2Factory(conceptRepository, definitionRepository, questionT2Repository, answerT2Repository),
                new Type3Factory(conceptRepository, definitionRepository, justificationRepository, questionT3Repository, answerT3Repository)
        );
    }

}
