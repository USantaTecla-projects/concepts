package com.example.backend.api.core.question.models.types;

import com.example.backend.api.core.question.models.Question;
import lombok.Data;

@Data
public class QuestionT1 extends Question {

    private boolean input;

    public QuestionT1() {
        super();
    }

    @Override
    public String generateQuestion() {
        return null;
    }
}
