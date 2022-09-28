package com.example.backend.api.resources.exam.resources.question.model.specific;

import com.example.backend.api.resources.exam.resources.question.model.Question;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class QuestionT2 extends Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonIgnore
    private Long conceptID;
    private String conceptText;
    @JsonIgnore
    private Long definitionID;
    private String definitionText;

    public QuestionT2() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConceptID() {
        return conceptID;
    }

    public void setConceptID(Long conceptID) {
        this.conceptID = conceptID;
    }

    public String getConceptText() {
        return conceptText;
    }

    public void setConceptText(String conceptText) {
        this.conceptText = conceptText;
    }

    public Long getDefinitionID() {
        return definitionID;
    }

    public void setDefinitionID(Long definitionID) {
        this.definitionID = definitionID;
    }

    public String getDefinitionText() {
        return definitionText;
    }

    public void setDefinitionText(String definitionText) {
        this.definitionText = definitionText;
    }

    @Override
    public String toString() {
        return "QuestionT2{" +
                "id=" + id +
                ", conceptID=" + conceptID +
                ", conceptText='" + conceptText + '\'' +
                ", definitionID=" + definitionID +
                ", definitionText='" + definitionText + '\'' +
                '}';
    }
}
