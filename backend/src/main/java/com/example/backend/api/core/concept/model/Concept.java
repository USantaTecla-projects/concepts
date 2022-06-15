package com.example.backend.api.core.concept.model;

import com.example.backend.api.core.answer.model.Answer;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="concept")
public class Concept {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String text;

    @OneToMany
    private List<Answer> answers;


    public Concept() {
    }

    public Concept(Long id, String text, List<Answer> answers) {
        this.id = id;
        this.text = text;
        this.answers = answers;
    }

    public Concept(String text, List<Answer> answers) {
        this.text = text;
        this.answers = answers;
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
        answers.add(answer);
    }

    /**
     * Remove an Answer from the list.
     *
     * @param answer The Answer to remove.
     */
    public void removeAnswer(Answer answer) {
        answers.remove(answer);
    }

    /**
     * Check if an Answer is in the list.
     *
     * @param answer The Answer to look for.
     * @return True if is in the list, else false.
     */
    public boolean containsAnswer(Answer answer) {
        return answers.contains(answer);
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Concept{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", answers=" + answers +
                '}';
    }
}
