package com.example.backend.api.resources.question.models;

import com.example.backend.api.resources.question.visitor.Visitor;

import javax.persistence.*;

@Entity
public class QuestionT0 extends Question {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;


    @Override
    public void accept(Visitor visitor) {
        visitor.visitQuestionT0(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
