package com.example.backend.api.resources.exam.module.question.model.specific;

import com.example.backend.api.resources.exam.module.answer.model.Answer;
import com.example.backend.api.resources.exam.module.answer.model.specific.AnswerT1;
import com.example.backend.api.resources.exam.module.question.model.Question;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="questionT1")
public class QuestionT1 extends Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long conceptID;

    @Transient
    private String conceptText;

    private Long definitionID;

    @Transient
    private String incorrectDefinitionText;

    @OneToMany
    private List<AnswerT1> answerT1List;
    public QuestionT1() {
    }

    @Override
    public void addAnswer(Answer answer) {
        answerT1List.add((AnswerT1) answer);
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

    @Override
    public String toString() {
        return "QuestionT1{" +
                "id=" + id +
                ", conceptID=" + conceptID +
                ", conceptText='" + conceptText + '\'' +
                ", definitionID=" + definitionID +
                ", incorrectDefinitionText='" + incorrectDefinitionText + '\'' +
                ", answerT1=" + answerT1List +
                ", type=" + type +
                ", isFilled=" + isFilled +
                '}';
    }


}
