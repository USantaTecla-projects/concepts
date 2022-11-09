package com.example.backend.api.resources.exam.domain.family.question.model.specific;

import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.api.resources.exam.domain.family.answer.model.specific.AnswerT1;
import com.example.backend.api.resources.exam.domain.family.answer.model.specific.AnswerT2;
import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
public class QuestionT2 extends Question {

    private Long conceptID;

    @Transient
    private String conceptText;

    private Long definitionID;

    @Transient
    private String definitionText;

    @ManyToOne
    @JsonManagedReference
    private AnswerT2 answerT2;

    public QuestionT2() {
    }

    @Override
    public void setAnswer(Answer answer) {
        AnswerT2 answerT2 = (AnswerT2) answer;
        this.setAnswerT2(answerT2);
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

    public AnswerT2 getAnswerT2() {
        return answerT2;
    }

    public void setAnswerT2(AnswerT2 answerT2) {
        this.answerT2 = answerT2;
    }

    @Override
    public String toString() {
        return "QuestionT2{" +
                ", conceptID=" + conceptID +
                ", conceptText='" + conceptText + '\'' +
                ", definitionID=" + definitionID +
                ", definitionText='" + definitionText + '\'' +
                ", answerT2=" + answerT2 +
                ", type=" + type +
                ", isFilled=" + isFilled +
                '}';
    }


}
