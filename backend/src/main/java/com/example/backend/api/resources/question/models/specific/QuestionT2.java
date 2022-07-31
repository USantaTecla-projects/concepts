package com.example.backend.api.resources.question.models.specific;

import com.example.backend.api.resources.question.models.Question;
import com.example.backend.api.resources.question.models.QuestionType;
import com.example.backend.api.resources.question.visitor.Visitor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

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
    public void accept(Visitor visitor, Map<QuestionType, List<Question>> questionReferences) {
        visitor.generateQuestionT2(this, questionReferences);
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
