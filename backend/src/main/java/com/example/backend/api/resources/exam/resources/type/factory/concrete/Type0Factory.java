package com.example.backend.api.resources.exam.resources.type.factory.concrete;

import com.example.backend.api.resources.exam.resources.question.repository.QuestionT0Repository;
import com.example.backend.api.resources.exam.resources.type.factory.TypeFactory;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.exam.resources.question.filler.QuestionFiller;
import com.example.backend.api.resources.exam.resources.question.filler.specific.QuestionT0QuestionFiller;
import com.example.backend.api.resources.exam.resources.question.model.Question;
import com.example.backend.api.resources.exam.resources.question.model.specific.QuestionT0;
import com.example.backend.api.resources.exam.resources.type.TypeData;

public class Type0Factory implements TypeFactory {

    private final ConceptRepository conceptRepository;

    private final QuestionT0Repository questionT0Repository;

    public Type0Factory(ConceptRepository conceptRepository, QuestionT0Repository questionT0Repository) {
        this.conceptRepository = conceptRepository;
        this.questionT0Repository = questionT0Repository;
    }

    @Override
    public Question createQuestion() {
        return new QuestionT0();
    }

    @Override
    public QuestionFiller createFiller() {
        return new QuestionT0QuestionFiller(conceptRepository, questionT0Repository);
    }

    @Override
    public TypeData createNumberOfAvailableQuestions() {
        return new TypeData(conceptRepository.countAvailableType0Questions());
    }
}
