package com.example.backend.api.resources.exam.module.question.generator;

import com.example.backend.api.resources.exam.module.question.model.Question;

import java.util.List;


public interface QuestionGenerator {
    Question generateQuestion(List<Question> alreadyGeneratedQuestions);
}
