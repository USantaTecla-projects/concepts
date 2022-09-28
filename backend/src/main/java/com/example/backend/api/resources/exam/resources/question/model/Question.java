package com.example.backend.api.resources.exam.resources.question.model;

import com.example.backend.api.resources.exam.resources.answer.model.Answer;
import com.example.backend.api.resources.exam.resources.type.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Question {

    private Type type;
    @JsonIgnore
    private boolean isFilled;

    private Answer answer;


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }

}
