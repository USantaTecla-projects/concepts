package com.example.backend.api.resources.question.type.factory;

import com.example.backend.api.resources.knowledge.answer.AnswerRepository;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.justification.JustificationRepository;
import com.example.backend.api.resources.question.type.factory.concrete.Type0Factory;
import com.example.backend.api.resources.question.type.factory.concrete.Type1Factory;
import com.example.backend.api.resources.question.type.factory.concrete.Type2Factory;
import com.example.backend.api.resources.question.type.factory.concrete.Type3Factory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeFactoryProvider {

    private final ConceptRepository conceptRepository;

    private final AnswerRepository answerRepository;

    private final JustificationRepository justificationRepository;

    public TypeFactoryProvider(
            ConceptRepository conceptRepository,
            AnswerRepository answerRepository,
            JustificationRepository justificationRepository
    ) {
        this.conceptRepository = conceptRepository;
        this.answerRepository = answerRepository;
        this.justificationRepository = justificationRepository;
    }

    public List<TypeFactory> getTypeAbstractFactory() {
        return List.of(
                new Type0Factory(conceptRepository),
                new Type1Factory(conceptRepository, answerRepository),
                new Type2Factory(conceptRepository, answerRepository),
                new Type3Factory(conceptRepository, answerRepository, justificationRepository)
        );
    }

}
