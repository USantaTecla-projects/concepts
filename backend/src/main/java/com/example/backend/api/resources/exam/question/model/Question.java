package com.example.backend.api.resources.exam.question.model;

import com.example.backend.api.resources.exam.question.filler.Filler;
import com.example.backend.api.resources.exam.type.QuestionType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public abstract class Question {

    private QuestionType type;
    @JsonIgnore
    private boolean isFilled;

    private String questionAsString;

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
