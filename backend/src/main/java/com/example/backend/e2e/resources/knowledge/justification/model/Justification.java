package com.example.backend.e2e.resources.knowledge.justification.model;

import javax.persistence.*;

@Entity
@Table(name="justification")
public class Justification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Column
    private Boolean isCorrect;

    @Column(columnDefinition = "TEXT")
    private String error;

    @Column(name = "concept_id")
    private Long conceptID;

    @Column(name = "definition_id")
    private Long definitionID;

    public Justification(Long id, String text, Boolean isCorrect, String error, Long conceptID, Long definitionID) {
        this.id = id;
        this.text = text;
        this.isCorrect = isCorrect;
        this.error = error;
        this.conceptID = conceptID;
        this.definitionID = definitionID;
    }

    public Justification(String text, Boolean isCorrect, String error, Long conceptID, Long definitionID) {
        this.text = text;
        this.isCorrect = isCorrect;
        this.error = error;
        this.conceptID = conceptID;
        this.definitionID = definitionID;
    }

    public Justification(String text, Boolean isCorrect, String error) {
        this.text = text;
        this.isCorrect = isCorrect;
        this.error = error;
    }



    public Justification() {
    }

    public Justification(String text, Boolean isCorrect, Long conceptID, Long definitionID) {
        this.text = text;
        this.isCorrect = isCorrect;
        this.conceptID = conceptID;
        this.definitionID = definitionID;
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

    public Long getConceptID() {
        return conceptID;
    }

    public void setConceptID(Long conceptID) {
        this.conceptID = conceptID;
    }

    public Long getDefinitionID() {
        return definitionID;
    }

    public void setDefinitionID(Long definitionID) {
        this.definitionID = definitionID;
    }

    @Override
    public String toString() {
        return "Justification{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", isCorrect=" + isCorrect +
                ", error='" + error + '\'' +
                ", conceptID=" + conceptID +
                ", definitionID=" + definitionID +
                '}';
    }
}
