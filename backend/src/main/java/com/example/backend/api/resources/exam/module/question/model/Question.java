package com.example.backend.api.resources.exam.module.question.model;

import com.example.backend.api.resources.exam.module.type.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Question {

    protected Type type;
    @JsonIgnore
    protected boolean isFilled;


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
