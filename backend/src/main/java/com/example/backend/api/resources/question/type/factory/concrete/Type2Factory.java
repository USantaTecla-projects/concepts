package com.example.backend.api.resources.question.type.factory.concrete;

import com.example.backend.api.resources.knowledge.answer.AnswerRepository;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.question.filler.Filler;
import com.example.backend.api.resources.question.filler.specific.QuestionT2Filler;
import com.example.backend.api.resources.question.models.Question;
import com.example.backend.api.resources.question.models.specific.QuestionT2;
import com.example.backend.api.resources.question.type.TypeData;
import com.example.backend.api.resources.question.type.factory.TypeFactory;

public class Type2Factory implements TypeFactory {

    private final ConceptRepository conceptRepository;

    private final AnswerRepository answerRepository;

    public Type2Factory(
            ConceptRepository conceptRepository,
            AnswerRepository answerRepository
    ) {
        this.conceptRepository = conceptRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public Question createQuestion() {
        return new QuestionT2();
    }

    @Override
    public Filler createFiller() {
        return new QuestionT2Filler(conceptRepository, answerRepository);
    }

    @Override
    public TypeData createNumberOfAvailableQuestions() {
        return new TypeData(answerRepository.countAvailableType2Questions());
    }
}
