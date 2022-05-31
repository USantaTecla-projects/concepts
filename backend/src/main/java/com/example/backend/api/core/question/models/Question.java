package com.example.backend.api.core.question.models;

import com.example.backend.api.core.concepts.models.Concept;

import javax.persistence.*;

@Entity
public class Question implements IQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Concept concept;

    @Override
    public String generateQuestion() {
        return null;
    }
}
