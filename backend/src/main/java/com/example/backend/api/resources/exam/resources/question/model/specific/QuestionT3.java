package com.example.backend.api.resources.exam.resources.question.model.specific;

import com.example.backend.api.resources.exam.resources.question.model.Question;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="questionT3")
public class QuestionT3 extends Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    private Long conceptID;

    @Transient
    private String conceptText;

    @JsonIgnore
    private Long definitionID;

    @Transient
    private String incorrectDefinitionText;

    @JsonIgnore
    private Long justificationID;

    @Transient
    private String justificationText;

    public QuestionT3() {
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

    public Long getJustificationID() {
        return justificationID;
    }

    public void setJustificationID(Long justificationID) {
        this.justificationID = justificationID;
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
                ", conceptID=" + conceptID +
                ", conceptText='" + conceptText + '\'' +
                ", definitionID=" + definitionID +
                ", incorrectDefinitionText='" + incorrectDefinitionText + '\'' +
                ", justificationID=" + justificationID +
                ", justificationText='" + justificationText + '\'' +
                '}';
    }
}
