package com.example.backend.api.resources.knowledge.justification.model;

import javax.persistence.*;

@Entity
@Table(name="justification")
public class Justification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String text;

    @Column
    private Boolean isCorrect;

    @Column
    private String error;

    @Column
    private Long conceptId;

    @Column
    private Long answerId;

    public Justification(Long id, String text, Boolean isCorrect, String error, Long conceptId, Long answerId) {
        this.id = id;
        this.text = text;
        this.isCorrect = isCorrect;
        this.error = error;
        this.conceptId = conceptId;
        this.answerId = answerId;
    }

    public Justification(String text, Boolean isCorrect, String error, Long conceptId, Long answerId) {
        this.text = text;
        this.isCorrect = isCorrect;
        this.error = error;
        this.conceptId = conceptId;
        this.answerId = answerId;
    }



    public Justification() {
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

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Long getConceptId() {
        return conceptId;
    }

    public void setConceptId(Long conceptId) {
        this.conceptId = conceptId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }
}
