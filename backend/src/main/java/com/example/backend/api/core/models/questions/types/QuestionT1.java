package com.example.backend.api.core.models.questions.types;

import com.example.backend.api.core.models.questions.Question;
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
