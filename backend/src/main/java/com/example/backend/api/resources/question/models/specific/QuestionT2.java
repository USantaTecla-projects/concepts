package com.example.backend.api.resources.question.models.specific;

import com.example.backend.api.resources.question.models.Question;
import com.example.backend.api.resources.question.filler.Filler;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
public class QuestionT2 extends Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonIgnore
    private Long conceptId;
    private String conceptText;
    @JsonIgnore
    private Long answerId;
    private String answerText;

    public QuestionT2() {
    }

    @Override
    public void accept(Filler filler, List<Question> questions) {
        filler.fillQuestion(this, questions);
    }

    @Override
    public String questionAsString() {
        return "¿Es correcto afirmar que " + conceptText + " es " + answerText + "?";
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

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    @Override
    public String toString() {
        return "QuestionT2{" +
                "id=" + id +
                ", conceptText='" + conceptText + '\'' +
                ", answerText='" + answerText + '\'' +
                '}';
    }
}
