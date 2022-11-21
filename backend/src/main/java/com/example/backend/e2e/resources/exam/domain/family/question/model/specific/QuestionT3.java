package com.example.backend.e2e.resources.exam.domain.family.question.model.specific;

import com.example.backend.e2e.resources.exam.domain.factory.Type;
import com.example.backend.e2e.resources.exam.domain.family.question.model.Question;

import javax.persistence.*;

@Entity
public class QuestionT3 extends Question {
    private Long conceptID;

    @Transient
    private String conceptText;

    private Long definitionID;

    @Transient
    private String incorrectDefinitionText;

    private Long justificationID;

    @Transient
    private String justificationText;

    public QuestionT3() {
    }

    public QuestionT3(Type type, Long conceptID, String conceptText, Long definitionID, String incorrectDefinitionText, Long justificationID, String justificationText) {
        super(type);
        this.conceptID = conceptID;
        this.conceptText = conceptText;
        this.definitionID = definitionID;
        this.incorrectDefinitionText = incorrectDefinitionText;
        this.justificationID = justificationID;
        this.justificationText = justificationText;
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
                "conceptID=" + conceptID +
                ", conceptText='" + conceptText + '\'' +
                ", definitionID=" + definitionID +
                ", incorrectDefinitionText='" + incorrectDefinitionText + '\'' +
                ", justificationID=" + justificationID +
                ", justificationText='" + justificationText + '\'' +
                ", type=" + type +
                ", isFilled=" + isFilled +
                '}';
    }
}
