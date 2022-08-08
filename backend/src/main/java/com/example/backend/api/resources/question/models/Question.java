package com.example.backend.api.resources.question.models;

import com.example.backend.api.resources.question.filler.Filler;
import com.example.backend.api.resources.question.type.QuestionType;
import com.example.backend.api.resources.question.type.TypeData;

import java.util.List;

public abstract class Question {

    private QuestionType type;

    private boolean isFilled;

    private String questionAsString;

    public abstract void accept(Filler filler, List<Question> questions);

    public abstract String questionAsString();


    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }

    public String getQuestionAsString() {
        return questionAsString;
    }

    public void setQuestionAsString(String questionAsString) {
        this.questionAsString = questionAsString;
    }
}
