package com.example.backend.api.resources.exam.resources.question.model.specific;

import com.example.backend.api.resources.exam.resources.question.model.Question;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="questionT0")
public class QuestionT0 extends Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    private Long conceptID;

    @Transient
    private String conceptText;


    public QuestionT0() {
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
                '}';
    }
}
