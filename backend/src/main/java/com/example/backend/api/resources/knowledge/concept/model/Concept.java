package com.example.backend.api.resources.knowledge.concept.model;

import com.example.backend.api.resources.knowledge.definition.model.Definition;
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
    private List<Definition> definitionList;


    public Concept() {
    }

    public Concept(Long id, String text, List<Definition> definitionList) {
        this.id = id;
        this.text = text;
        this.definitionList = definitionList;
    }

    public Concept(String text, List<Definition> definitionList) {
        this.text = text;
        this.definitionList = definitionList;
    }

    public Concept(String text) {
        this.text = text;
    }

    /**
     * Add a new definition to the list.
     *
     * @param definition The definition to add.
     */
    public void addDefinition(Definition definition) {
        if(this.definitionList == null) this.definitionList = new LinkedList<>();
        definitionList.add(definition);
    }

    /**
     * Remove an definition from the list.
     *
     * @param definition The definition to remove.
     */
    public void removeDefinition(Definition definition) {
        definitionList.remove(definition);
    }

    /**
     * Check if an definition is in the list.
     *
     * @param definition The definition to look for.
     * @return True if is in the list, else false.
     */
    public boolean containsDefinition(Definition definition) {
        return definitionList.contains(definition);
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

    public List<Definition> getDefinitionList() {
        return definitionList;
    }

    public void setDefinitionList(List<Definition> definitionList) {
        this.definitionList = definitionList;
    }

    @Override
    public String toString() {
        return "Concept{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", definitions=" + definitionList +
                '}';
    }
}
