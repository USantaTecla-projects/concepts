package com.example.backend.api.resources.exam.module.question.model.specific;

import com.example.backend.api.resources.exam.module.answer.model.Answer;
import com.example.backend.api.resources.exam.module.answer.model.specific.AnswerT0;
import com.example.backend.api.resources.exam.module.question.model.Question;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="questionT0")
public class QuestionT0 extends Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long conceptID;

    @Transient
    private String conceptText;

    @OneToMany
    private List<AnswerT0> answerT0List;


    public QuestionT0() {
    }

    @Override
    public void addAnswer(Answer answer) {
        answerT0List.add((AnswerT0) answer);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                "id=" + id +
                ", conceptID=" + conceptID +
                ", conceptText='" + conceptText + '\'' +
                ", answerT0=" + answerT0List +
                ", type=" + type +
                ", isFilled=" + isFilled +
                '}';
    }


}
