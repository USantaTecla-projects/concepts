package com.example.backend.api.resources.knowledge.answer.model;

import com.example.backend.api.resources.knowledge.justification.model.Justification;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Column
    private Boolean isCorrect;

    @Column
    private Long conceptId;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Justification> justificationList;

    public Answer() {
    }

    public Answer(Long id, String text, Boolean isCorrect, Long conceptId) {
        this.id = id;
        this.text = text;
        this.isCorrect = isCorrect;
        this.conceptId = conceptId;
    }

    public Answer(String text, Boolean isCorrect, Long conceptId, List<Justification> justificationList) {
        this.text = text;
        this.isCorrect = isCorrect;
        this.conceptId = conceptId;
        this.justificationList = justificationList;
    }

    public Answer(Long id, String text, Boolean isCorrect, Long conceptId, List<Justification> justificationList) {
        this.id = id;
        this.text = text;
        this.isCorrect = isCorrect;
        this.conceptId = conceptId;
        this.justificationList = justificationList;
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

    public void addJustification(Justification justification) {
        if (this.justificationList == null) this.justificationList = new LinkedList<>();
        justificationList.add(justification);
    }

    public void removeJustification(Justification justification) {
        justificationList.remove(justification);
    }

    public boolean containsJustification(Justification justification) {
        return justificationList.contains(justification);
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

    public Long getConceptId() {
        return conceptId;
    }

    public void setConceptId(Long conceptId) {
        this.conceptId = conceptId;
    }

    public List<Justification> getJustificationList() {
        return justificationList;
    }

    public void setJustificationList(List<Justification> justification) {
        this.justificationList = justification;
    }
}
