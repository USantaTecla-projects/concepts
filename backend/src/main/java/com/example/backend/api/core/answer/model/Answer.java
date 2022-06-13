package com.example.backend.api.core.answer.model;

import com.example.backend.api.core.justification.model.Justification;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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

    @OneToMany
    private List<Justification> justifications;

    public Answer() {
    }

    public Answer(Long id, String text, Boolean isCorrect, Long conceptId) {
        this.id = id;
        this.text = text;
        this.isCorrect = isCorrect;
        this.conceptId = conceptId;
    }

    public Answer(String text, Boolean isCorrect, Long conceptId, List<Justification> justifications) {
        this.text = text;
        this.isCorrect = isCorrect;
        this.conceptId = conceptId;
        this.justifications = justifications;
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
        justifications.add(justification);
    }

    public void removeJustification(Justification justification) {
        justifications.remove(justification);
    }

    public boolean containsJustification(Justification justification){
        return justifications.contains(justification);
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

    public List<Justification> getJustifications() {
        return justifications;
    }

    public void setJustifications(List<Justification> justification) {
        this.justifications = justification;
    }
}
