package com.example.backend.api.resources.question.type.factory.concrete;

import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.question.filler.Filler;
import com.example.backend.api.resources.question.filler.specific.QuestionT0Filler;
import com.example.backend.api.resources.question.models.Question;
import com.example.backend.api.resources.question.models.specific.QuestionT0;
import com.example.backend.api.resources.question.type.TypeData;
import com.example.backend.api.resources.question.type.factory.TypeFactory;

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
