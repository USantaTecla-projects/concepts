package com.example.backend.api.resources.exam.type.factory.concrete;

import com.example.backend.api.resources.exam.type.factory.TypeFactory;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.exam.question.filler.Filler;
import com.example.backend.api.resources.exam.question.filler.specific.QuestionT0Filler;
import com.example.backend.api.resources.exam.question.model.Question;
import com.example.backend.api.resources.exam.question.model.specific.QuestionT0;
import com.example.backend.api.resources.exam.type.TypeData;

public class Type0Factory implements TypeFactory {

    private final ConceptRepository conceptRepository;

    public Type0Factory(ConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
    }

    @Override
    public Question createQuestion() {
        return new QuestionT0();
    }

    @Override
    public Filler createFiller() {
        return new QuestionT0Filler(conceptRepository);
    }

    @Override
    public TypeData createNumberOfAvailableQuestions() {
        return new TypeData(conceptRepository.countAvailableType0Questions());
    }
}
