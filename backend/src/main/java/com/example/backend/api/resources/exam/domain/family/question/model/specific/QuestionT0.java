package com.example.backend.api.resources.exam.domain.family.question.model.specific;

import com.example.backend.api.resources.exam.domain.factory.Type;
import com.example.backend.api.resources.exam.domain.family.question.model.Question;

import javax.persistence.*;

@Entity
public class QuestionT0 extends Question {

    private Long conceptID;

    @Transient
    private String conceptText;

    public QuestionT0() {
    }

    public QuestionT0(Type type, Long conceptID, String conceptText) {
        super(type);
        this.conceptID = conceptID;
        this.conceptText = conceptText;
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

    @Override
    public String toString() {
        return "QuestionT0{" +
                "conceptID=" + conceptID +
                ", conceptText='" + conceptText + '\'' +
                ", type=" + type +
                ", isFilled=" + isFilled +
                '}';
    }
}
