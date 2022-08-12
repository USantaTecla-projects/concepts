package com.example.backend.api.resources.exam.type.factory;

import com.example.backend.api.resources.exam.question.filler.Filler;
import com.example.backend.api.resources.exam.question.model.Question;
import com.example.backend.api.resources.exam.type.TypeData;

public interface TypeFactory {

    Question createQuestion();

    Filler createFiller();

    TypeData createNumberOfAvailableQuestions();
}
