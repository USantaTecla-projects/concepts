package com.example.backend.api.resources.exam.domain.family.question.tools.filler;

import com.example.backend.api.resources.exam.domain.family.question.model.Question;

public interface QuestionFiller {

    Question fillQuestionWithData(Question question);
}
