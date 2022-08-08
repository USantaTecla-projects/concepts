package com.example.backend.api.resources.question.filler;

import com.example.backend.api.resources.question.models.Question;

import java.util.List;


public interface Filler {
    void fillQuestion(Question question, List<Question> questions);
}
