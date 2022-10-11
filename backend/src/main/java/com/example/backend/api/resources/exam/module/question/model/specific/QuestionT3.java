package com.example.backend.api.resources.exam.module.question.model.specific;

import com.example.backend.api.resources.exam.module.answer.model.Answer;
import com.example.backend.api.resources.exam.module.answer.model.specific.AnswerT3;
import com.example.backend.api.resources.exam.module.question.model.Question;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany
    @JoinTable(name = "questiont3_answerT3_list")
    private List<AnswerT3> answerT3List;

    public QuestionT3() {
    }

    @Override
    public void addAnswer(Answer answer) {
        answerT3List.add((AnswerT3) answer);
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
                ", conceptID=" + conceptID +
                ", conceptText='" + conceptText + '\'' +
                ", definitionID=" + definitionID +
                ", incorrectDefinitionText='" + incorrectDefinitionText + '\'' +
                ", justificationID=" + justificationID +
                ", justificationText='" + justificationText + '\'' +
                ", answerT3=" + answerT3List +
                ", type=" + type +
                ", isFilled=" + isFilled +
                '}';
    }


}
