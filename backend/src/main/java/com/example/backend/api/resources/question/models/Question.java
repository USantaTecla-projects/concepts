package com.example.backend.api.resources.question.models;

import com.example.backend.api.resources.question.visitor.Visitor;

import java.util.List;
import java.util.Map;

public abstract class Question {

    private QuestionType type;

    public String questionAsString;

    public abstract void accept(Visitor visitor, Map<QuestionType, List<Question>> questionReferences);

    public abstract String questionAsString();

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public String getQuestionAsString() {
        return questionAsString;
    }

    public void setQuestionAsString(String questionAsString) {
        this.questionAsString = questionAsString;
    }
}
