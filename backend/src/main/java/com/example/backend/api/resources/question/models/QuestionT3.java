package com.example.backend.api.resources.question.models;

import com.example.backend.api.resources.question.visitor.Visitor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class QuestionT3 extends Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String type;

    private String conceptText;

    private String incorrectAnswerText;

    private String justificationText;

    public QuestionT3() {
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitQuestionT3(this);
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

    public String getIncorrectAnswerText() {
        return incorrectAnswerText;
    }

    public void setIncorrectAnswerText(String incorrectAnswerText) {
        this.incorrectAnswerText = incorrectAnswerText;
    }

    public String getJustificationText() {
        return justificationText;
    }

    public void setJustificationText(String justificationText) {
        this.justificationText = justificationText;
    }
}
