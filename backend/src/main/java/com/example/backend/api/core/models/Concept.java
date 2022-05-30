package com.example.backend.api.core.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Concept {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    @OneToMany
    private List<Answer> answer;



}
