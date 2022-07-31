package com.example.backend.api.resources.question.models.specific;

import com.example.backend.api.resources.question.models.Question;
import com.example.backend.api.resources.question.models.QuestionType;
import com.example.backend.api.resources.question.visitor.Visitor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import java.util.Map;

@Entity
public class QuestionT3 extends Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonIgnore
    private Long conceptId;
    private String conceptText;
    @JsonIgnore
    private Long answerId;
    private String incorrectAnswerText;
    @JsonIgnore
    private Long justificationId;
    private String justificationText;

    public QuestionT3() {
    }

    @Override
    public void accept(Visitor visitor, Map<QuestionType, List<Question>> questionReferences) {
        visitor.generateQuestionT3(this, questionReferences);
    }

    @Override
    public String questionAsString() {
        return "¿Es cierto que " + conceptText + " no es " + incorrectAnswerText + " porque " + justificationText + "?";
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

    public Long getJustificationId() {
        return justificationId;
    }

    public void setJustificationId(Long justificationId) {
        this.justificationId = justificationId;
    }

    public String getJustificationText() {
        return justificationText;
    }

    public void setJustificationText(String justificationText) {
        this.justificationText = justificationText;
    }

    @Override
    public String toString() {
        return "QuestionT3{" +
                "id=" + id +
                ", conceptText='" + conceptText + '\'' +
                ", incorrectAnswerText='" + incorrectAnswerText + '\'' +
                ", justificationText='" + justificationText + '\'' +
                '}';
    }
}
