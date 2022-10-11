package com.example.backend.api.resources.exam.module.question.model.specific;

import com.example.backend.api.resources.exam.module.answer.model.Answer;
import com.example.backend.api.resources.exam.module.answer.model.specific.AnswerT0;
import com.example.backend.api.resources.exam.module.question.model.Question;

import javax.persistence.*;
import java.util.List;

@Entity
public class QuestionT0 extends Question {


    private Long conceptID;

    @Column(columnDefinition = "TEXT")
    private String conceptText;

    @OneToMany
    @JoinTable(name = "questiont0_answerT0_list")
    private List<AnswerT0> answerT0List;


    public QuestionT0() {
    }

    @Override
    public void addAnswer(Answer answer) {
        answerT0List.add((AnswerT0) answer);
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
                ", conceptID=" + conceptID +
                ", conceptText='" + conceptText + '\'' +
                ", answerT0=" + answerT0List +
                ", type=" + type +
                ", isFilled=" + isFilled +
                '}';
    }


}
