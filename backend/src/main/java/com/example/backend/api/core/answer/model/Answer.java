package com.example.backend.api.core.answer.model;

import com.example.backend.api.core.concept.model.Concept;
import com.example.backend.api.core.justification.models.Justification;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String text;

    @Column
    private Boolean isCorrect;

    @Column
    private Long conceptId;

    @OneToOne
    private Justification justification;

    public Answer() {
    }

    public Answer(Long id, String text, Boolean isCorrect, Long conceptId) {
        this.id = id;
        this.text = text;
        this.isCorrect = isCorrect;
        this.conceptId = conceptId;
    }

    public Answer(String text, Boolean isCorrect, Long conceptId) {
        this.text = text;
        this.isCorrect = isCorrect;
        this.conceptId = conceptId;
    }

    public Answer(String text, Boolean isCorrect) {
        this.text = text;
        this.isCorrect = isCorrect;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public Long getConceptId() {
        return conceptId;
    }

    public void setConceptId(Long conceptId) {
        this.conceptId = conceptId;
    }

    public Justification getJustification() {
        return justification;
    }

    public void setJustification(Justification justification) {
        this.justification = justification;
    }
}
