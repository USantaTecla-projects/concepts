package com.example.backend.api.resources.exam.type.factory.concrete;

import com.example.backend.api.resources.exam.type.factory.TypeFactory;
import com.example.backend.api.resources.knowledge.answer.AnswerRepository;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.exam.question.filler.Filler;
import com.example.backend.api.resources.exam.question.filler.specific.QuestionT2Filler;
import com.example.backend.api.resources.exam.question.model.Question;
import com.example.backend.api.resources.exam.question.model.specific.QuestionT2;
import com.example.backend.api.resources.exam.type.TypeData;

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
