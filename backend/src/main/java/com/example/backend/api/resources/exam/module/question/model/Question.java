package com.example.backend.api.resources.exam.module.question.model;

import com.example.backend.api.resources.exam.module.answer.model.Answer;
import com.example.backend.api.resources.exam.module.type.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    protected Type type;

    @JsonIgnore
    protected boolean isFilled;

    public abstract void addAnswer(Answer answer);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
