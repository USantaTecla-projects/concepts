package com.example.backend.api.resources.knowledge.concept.model;

import com.example.backend.api.resources.knowledge.answer.model.Answer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="concept")
public class Concept {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String text;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Answer> answerList;


    public Concept() {
    }

    public Concept(Long id, String text, List<Answer> answerList) {
        this.id = id;
        this.text = text;
        this.answerList = answerList;
    }

    public Concept(String text, List<Answer> answerList) {
        this.text = text;
        this.answerList = answerList;
    }

    public Concept(String text) {
        this.text = text;
    }

    /**
     * Add a new Answer to the list.
     *
     * @param answer The Answer to add.
     */
    public void addAnswer(Answer answer) {
        if(this.answerList == null) this.answerList = new LinkedList<>();
        answerList.add(answer);
    }

    /**
     * Remove an Answer from the list.
     *
     * @param answer The Answer to remove.
     */
    public void removeAnswer(Answer answer) {
        answerList.remove(answer);
    }

    /**
     * Check if an Answer is in the list.
     *
     * @param answer The Answer to look for.
     * @return True if is in the list, else false.
     */
    public boolean containsAnswer(Answer answer) {
        return answerList.contains(answer);
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

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    @Override
    public String toString() {
        return "Concept{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", answers=" + answerList +
                '}';
    }
}
