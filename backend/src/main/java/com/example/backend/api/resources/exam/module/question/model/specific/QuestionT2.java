package com.example.backend.api.resources.exam.module.question.model.specific;

import com.example.backend.api.resources.exam.module.answer.model.Answer;
import com.example.backend.api.resources.exam.module.answer.model.specific.AnswerT2;
import com.example.backend.api.resources.exam.module.question.model.Question;

import javax.persistence.*;
import java.util.List;

@Entity
public class QuestionT2 extends Question {

    private Long conceptID;

    @Transient
    private String conceptText;

    private Long definitionID;

    @Transient
    private String definitionText;

    @OneToMany
    @JoinTable(name = "questiont2_answerT2_list")
    private List<AnswerT2> answerT2List;

    public QuestionT2() {
    }

    @Override
    public void addAnswer(Answer answer) {
        answerT2List.add((AnswerT2) answer);
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
                ", conceptID=" + conceptID +
                ", conceptText='" + conceptText + '\'' +
                ", definitionID=" + definitionID +
                ", definitionText='" + definitionText + '\'' +
                ", answerT2=" + answerT2List +
                ", type=" + type +
                ", isFilled=" + isFilled +
                '}';
    }


}
