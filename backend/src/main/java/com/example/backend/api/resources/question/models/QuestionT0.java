package com.example.backend.api.resources.question.models;

import com.example.backend.api.resources.question.visitor.Visitor;

import javax.persistence.*;

@Entity
public class QuestionT0 extends Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String type;
    private String conceptText;

    public QuestionT0() {
    }
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConceptText() {
        return conceptText;
    }

    public void setConceptText(String conceptText) {
        this.conceptText = conceptText;
    }
}
