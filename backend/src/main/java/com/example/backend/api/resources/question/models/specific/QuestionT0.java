package com.example.backend.api.resources.question.models.specific;

import com.example.backend.api.resources.question.models.Question;
import com.example.backend.api.resources.question.filler.Filler;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
public class QuestionT0 extends Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    private Long conceptId;
    private String conceptText;


    public QuestionT0() {
    }

    @Override
    public void accept(Filler filler, List<Question> questions) {
        filler.fillQuestion(this, questions);
    }

    @Override
    public String questionAsString() {
        return "¿Qué es " + conceptText + "?";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConceptId() {
        return conceptId;
    }

    public void setConceptId(Long conceptId) {
        this.conceptId = conceptId;
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
                ", conceptText='" + conceptText + '\'' +
                '}';
    }
}
