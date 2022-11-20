package com.example.backend.e2e.resources.exam.domain.family.question.tools.generator;

import com.example.backend.e2e.resources.exam.domain.family.question.model.Question;

import java.util.List;


public interface QuestionGenerator {
    Question generateQuestion(List<Question> alreadyGeneratedQuestions);
}
