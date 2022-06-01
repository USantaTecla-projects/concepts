package com.example.backend.api.core.concept.model;

import com.example.backend.api.core.answer.model.Answer;

import javax.persistence.*;
import java.util.List;

@Entity
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
}