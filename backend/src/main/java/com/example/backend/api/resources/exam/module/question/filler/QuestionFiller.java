package com.example.backend.api.resources.exam.module.question.filler;

import com.example.backend.api.resources.exam.module.question.model.Question;

import java.util.List;


public interface QuestionFiller {
    void fillQuestion(Question question, List<Question> questions);
}
