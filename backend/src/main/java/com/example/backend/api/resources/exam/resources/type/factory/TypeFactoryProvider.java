package com.example.backend.api.resources.exam.resources.type.factory;

import com.example.backend.api.resources.exam.resources.question.repository.QuestionT0Repository;
import com.example.backend.api.resources.exam.resources.question.repository.QuestionT1Repository;
import com.example.backend.api.resources.exam.resources.question.repository.QuestionT2Repository;
import com.example.backend.api.resources.exam.resources.question.repository.QuestionT3Repository;
import com.example.backend.api.resources.exam.resources.type.factory.concrete.Type3Factory;
import com.example.backend.api.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.justification.JustificationRepository;
import com.example.backend.api.resources.exam.resources.type.factory.concrete.Type0Factory;
import com.example.backend.api.resources.exam.resources.type.factory.concrete.Type1Factory;
import com.example.backend.api.resources.exam.resources.type.factory.concrete.Type2Factory;
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

    public TypeFactoryProvider(
            ConceptRepository conceptRepository,
            DefinitionRepository definitionRepository,
            JustificationRepository justificationRepository,
            QuestionT0Repository questionT0Repository,
            QuestionT1Repository questionT1Repository,
            QuestionT2Repository questionT2Repository,
            QuestionT3Repository questionT3Repository
    ) {
        this.conceptRepository = conceptRepository;
        this.definitionRepository = definitionRepository;
        this.justificationRepository = justificationRepository;
        this.questionT0Repository = questionT0Repository;
        this.questionT1Repository = questionT1Repository;
        this.questionT2Repository = questionT2Repository;
        this.questionT3Repository = questionT3Repository;
    }

    public List<TypeFactory> getTypeAbstractFactories() {
        return List.of(
                new Type0Factory(conceptRepository, questionT0Repository),
                new Type1Factory(conceptRepository, definitionRepository, questionT1Repository),
                new Type2Factory(conceptRepository, definitionRepository, questionT2Repository),
                new Type3Factory(conceptRepository, definitionRepository, justificationRepository, questionT3Repository)
        );
    }

}
