package com.example.backend.api.resources.exam.question.model.specific;

import com.example.backend.api.resources.exam.question.model.Question;
import com.example.backend.api.resources.exam.question.filler.Filler;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
public class QuestionT1 extends Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonIgnore
    private Long conceptId;

    private String conceptText;
    @JsonIgnore
    private Long answerId;
    private String incorrectAnswerText;

    public QuestionT1() {
    }

    @Override
    public String questionAsString() {
        return "¿Por qué no es correcto afirmar que " + conceptText + " es " + incorrectAnswerText + "?";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConceptId() {
        return conceptId;
    }

    public void setConceptId(Long conceptId) {
        this.conceptId = conceptId;
    }

    public String getConceptText() {
        return conceptText;
    }

    public void setConceptText(String conceptText) {
        this.conceptText = conceptText;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public String getIncorrectAnswerText() {
        return incorrectAnswerText;
    }

    public void setIncorrectAnswerText(String incorrectAnswerText) {
        this.incorrectAnswerText = incorrectAnswerText;
    }

    @Override
    public String toString() {
        return "QuestionT1{" +
                "id=" + id +
                ", conceptText='" + conceptText + '\'' +
                ", incorrectAnswerText='" + incorrectAnswerText + '\'' +
                '}';
    }
}
