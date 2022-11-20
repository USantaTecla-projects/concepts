package com.example.backend.e2e.resources.knowledge.definition.model;

import com.example.backend.e2e.resources.knowledge.justification.model.Justification;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "definition")
public class Definition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Column
    private Boolean isCorrect;

    @Column(name = "concept_id")
    private Long conceptID;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Justification> justificationList;

    public Definition() {
    }

    public Definition(Long id, String text, Boolean isCorrect, Long conceptID) {
        this.id = id;
        this.text = text;
        this.isCorrect = isCorrect;
        this.conceptID = conceptID;
    }

    public Definition(String text, Boolean isCorrect, Long conceptID, List<Justification> justificationList) {
        this.text = text;
        this.isCorrect = isCorrect;
        this.conceptID = conceptID;
        this.justificationList = justificationList;
    }

    public Definition(Long id, String text, Boolean isCorrect, Long conceptID, List<Justification> justificationList) {
        this.id = id;
        this.text = text;
        this.isCorrect = isCorrect;
        this.conceptID = conceptID;
        this.justificationList = justificationList;
    }

    public Definition(String text, Boolean isCorrect, Long conceptID) {
        this.text = text;
        this.isCorrect = isCorrect;
        this.conceptID = conceptID;
    }

    public Definition(String text, Boolean isCorrect) {
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

    public Long getConceptID() {
        return conceptID;
    }

    public void setConceptID(Long conceptID) {
        this.conceptID = conceptID;
    }

    public List<Justification> getJustificationList() {
        return justificationList;
    }

    public void setJustificationList(List<Justification> justification) {
        this.justificationList = justification;
    }
}
