package com.example.backend.api.resources.exam.domain.family.question.model.specific;

import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.api.resources.exam.domain.family.answer.model.specific.AnswerT0;
import com.example.backend.api.resources.exam.domain.family.answer.model.specific.AnswerT1;
import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class QuestionT1 extends Question {

    private Long conceptID;

    @Transient
    private String conceptText;

    private Long definitionID;

    @Transient
    private String incorrectDefinitionText;

    @ManyToOne()
    @JsonManagedReference
    private AnswerT1 answerT1;

    public QuestionT1() {
    }

    @Override
    public void setAnswer(Answer answer) {
        AnswerT1 answerT1 = (AnswerT1) answer;
        this.setAnswerT1(answerT1);
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

    public AnswerT1 getAnswerT1() {
        return answerT1;
    }

    public void setAnswerT1(AnswerT1 answerT1) {
        this.answerT1 = answerT1;
    }

    @Override
    public String toString() {
        return "QuestionT1{" +
                ", conceptID=" + conceptID +
                ", conceptText='" + conceptText + '\'' +
                ", definitionID=" + definitionID +
                ", incorrectDefinitionText='" + incorrectDefinitionText + '\'' +
                ", answerT1=" + answerT1 +
                ", type=" + type +
                ", isFilled=" + isFilled +
                '}';
    }


}
