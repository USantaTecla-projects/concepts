package com.example.backend.api.resources.exam.domain.family.question.model.specific;

import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.factory.Type;

import javax.persistence.*;

@Entity
public class QuestionT2 extends Question {

    private Long conceptID;

    @Transient
    private String conceptText;

    private Long definitionID;

    @Transient
    private String definitionText;

    public QuestionT2() {
    }

    public QuestionT2(Type type, Long conceptID, String conceptText, Long definitionID, String definitionText) {
        super(type);
        this.conceptID = conceptID;
        this.conceptText = conceptText;
        this.definitionID = definitionID;
        this.definitionText = definitionText;
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
                "conceptID=" + conceptID +
                ", conceptText='" + conceptText + '\'' +
                ", definitionID=" + definitionID +
                ", definitionText='" + definitionText + '\'' +
                ", type=" + type +
                ", isFilled=" + isFilled +
                '}';
    }
}
