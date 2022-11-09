package com.example.backend.api.resources.exam.domain.family.question.model.specific;

import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.api.resources.exam.domain.family.answer.model.specific.AnswerT1;
import com.example.backend.api.resources.exam.domain.family.answer.model.specific.AnswerT3;
import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @ManyToOne
    @JsonManagedReference
    private AnswerT3 answerT3;

    public QuestionT3() {
    }

    @Override
    public void setAnswer(Answer answer) {
        AnswerT3 answerT3 = (AnswerT3) answer;
        this.setAnswerT3(answerT3);
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

    public AnswerT3 getAnswerT3() {
        return answerT3;
    }

    public void setAnswerT3(AnswerT3 answerT3) {
        this.answerT3 = answerT3;
    }

    @Override
    public String toString() {
        return "QuestionT3{" +
                ", conceptID=" + conceptID +
                ", conceptText='" + conceptText + '\'' +
                ", definitionID=" + definitionID +
                ", incorrectDefinitionText='" + incorrectDefinitionText + '\'' +
                ", justificationID=" + justificationID +
                ", justificationText='" + justificationText + '\'' +
                ", answerT3=" + answerT3 +
                ", type=" + type +
                ", isFilled=" + isFilled +
                '}';
    }


}
