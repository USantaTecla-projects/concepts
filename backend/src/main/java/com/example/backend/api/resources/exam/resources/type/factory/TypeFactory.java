package com.example.backend.api.resources.exam.resources.type.factory;

import com.example.backend.api.resources.exam.resources.question.filler.QuestionFiller;
import com.example.backend.api.resources.exam.resources.question.model.Question;
import com.example.backend.api.resources.exam.resources.type.TypeData;

public interface TypeFactory {

    Question createQuestion();

    QuestionFiller createFiller();

    TypeData createNumberOfAvailableQuestions();
}
