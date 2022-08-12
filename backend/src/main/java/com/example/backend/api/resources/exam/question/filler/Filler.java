package com.example.backend.api.resources.exam.question.filler;

import com.example.backend.api.resources.exam.question.model.Question;

import java.util.List;


public interface Filler {
    void fillQuestion(Question question, List<Question> questions);
}
