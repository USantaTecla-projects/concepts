package com.example.backend.api.resources.question.models;

import com.example.backend.api.resources.question.visitor.Visitor;

import javax.persistence.*;

@Entity
public class QuestionT2 extends Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String type;

    private String conceptText;
    private String answerText;

    public QuestionT2() {
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitQuestionT2(this);
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

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}
