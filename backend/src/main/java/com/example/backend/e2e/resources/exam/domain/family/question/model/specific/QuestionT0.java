package com.example.backend.e2e.resources.exam.domain.family.question.model.specific;

import com.example.backend.e2e.resources.exam.domain.family.question.model.Question;

import javax.persistence.*;

@Entity
public class QuestionT0 extends Question {


    private Long conceptID;

    @Transient
    private String conceptText;

    public QuestionT0() {
    }

    public QuestionT0(Long conceptID, String conceptText) {
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
}
