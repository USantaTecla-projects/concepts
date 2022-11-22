package com.example.backend.api.resources.exam.domain.family.question.model.specific;

import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.factory.Type;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class QuestionT1 extends Question {

    private Long conceptID;

    @Transient
    private String conceptText;

    private Long definitionID;

    @Transient
    private String incorrectDefinitionText;

    public QuestionT1() {
    }

    public QuestionT1(Type type, Long conceptID, String conceptText, Long definitionID, String incorrectDefinitionText) {
        super(type);
        this.conceptID = conceptID;
        this.conceptText = conceptText;
        this.definitionID = definitionID;
        this.incorrectDefinitionText = incorrectDefinitionText;
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
                "conceptID=" + conceptID +
                ", conceptText='" + conceptText + '\'' +
                ", definitionID=" + definitionID +
                ", incorrectDefinitionText='" + incorrectDefinitionText + '\'' +
                ", type=" + type +
                ", isFilled=" + isFilled +
                '}';
    }
}
