package com.example.backend.api.resources.question.type.factory.concrete;

import com.example.backend.api.resources.knowledge.answer.AnswerRepository;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.justification.JustificationRepository;
import com.example.backend.api.resources.question.filler.Filler;
import com.example.backend.api.resources.question.filler.specific.QuestionT3Filler;
import com.example.backend.api.resources.question.models.Question;
import com.example.backend.api.resources.question.models.specific.QuestionT3;
import com.example.backend.api.resources.question.type.TypeData;
import com.example.backend.api.resources.question.type.factory.TypeFactory;

public class Type3Factory implements TypeFactory {

    private final ConceptRepository conceptRepository;

    private final AnswerRepository answerRepository;

    private final JustificationRepository justificationRepository;

    public Type3Factory(
            ConceptRepository conceptRepository,
            AnswerRepository answerRepository,
            JustificationRepository justificationRepository
    ) {
        this.conceptRepository = conceptRepository;
        this.answerRepository = answerRepository;
        this.justificationRepository = justificationRepository;
    }

    @Override
    public Question createQuestion() {
        return new QuestionT3();
    }

    @Override
    public Filler createFiller() {
        return new QuestionT3Filler(conceptRepository, answerRepository, justificationRepository);
    }

    @Override
    public TypeData createNumberOfAvailableQuestions() {
        return new TypeData(justificationRepository.countAvailableType3Questions());
    }
}
