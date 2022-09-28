package com.example.backend.api.resources.exam.resources.question.model.specific;

import com.example.backend.api.resources.exam.resources.question.model.Question;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class QuestionT1 extends Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonIgnore
    private Long conceptID;

    private String conceptText;
    @JsonIgnore
    private Long definitionID;
    private String incorrectDefinitionText;

    public QuestionT1() {
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

    public String getIncorrectDefinitionText() {
        return incorrectDefinitionText;
    }

    public void setIncorrectDefinitionText(String incorrectDefinitionText) {
        this.incorrectDefinitionText = incorrectDefinitionText;
    }

    @Override
    public String toString() {
        return "QuestionT1{" +
                "id=" + id +
                ", conceptID=" + conceptID +
                ", conceptText='" + conceptText + '\'' +
                ", definitionID=" + definitionID +
                ", incorrectDefinitionText='" + incorrectDefinitionText + '\'' +
                '}';
    }
}
