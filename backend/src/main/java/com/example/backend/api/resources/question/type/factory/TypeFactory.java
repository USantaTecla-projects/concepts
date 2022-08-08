package com.example.backend.api.resources.question.type.factory;

import com.example.backend.api.resources.question.filler.Filler;
import com.example.backend.api.resources.question.models.Question;
import com.example.backend.api.resources.question.type.TypeData;

public interface TypeFactory {

    Question createQuestion();

    Filler createFiller();

    TypeData createNumberOfAvailableQuestions();
}
