package com.example.backend.api.core.answer.models;

import com.example.backend.api.core.justification.models.Justification;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    private boolean isCorrect;

    @OneToOne
    private Justification justification;

}
