package com.example.backend.api.resources.exam.domain.family.question.model.specific;

import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.api.resources.exam.domain.family.answer.model.specific.AnswerT0;
import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
public class QuestionT0 extends Question {


    private Long conceptID;

    @Transient
    private String conceptText;

    @ManyToOne()
    @JsonManagedReference
    private AnswerT0 answerT0;

    public QuestionT0() {
    }

    @Override
    public void setAnswer(Answer answer) {
        AnswerT0 answerT0 = (AnswerT0) answer;
        this.setAnswerT0(answerT0);
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

    public AnswerT0 getAnswerT0() {
        return answerT0;
    }

    public void setAnswerT0(AnswerT0 answerT0) {
        this.answerT0 = answerT0;
    }

    @Override
    public String toString() {
        return "QuestionT0{" +
                ", conceptID=" + conceptID +
                ", conceptText='" + conceptText + '\'' +
                ", answerT0=" + answerT0 +
                ", type=" + type +
                ", isFilled=" + isFilled +
                '}';
    }


}
