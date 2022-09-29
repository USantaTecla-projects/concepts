package com.example.backend.api.resources.exam.module.answer.model.specific;

import com.example.backend.api.resources.exam.module.answer.model.Answer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AnswerT2 extends Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Boolean reply;

    public AnswerT2() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getReply() {
        return reply;
    }

    public void setReply(Boolean reply) {
        this.reply = reply;
    }

    @Override
    public String toString() {
        return "AnswerT2{" +
                "id=" + id +
                ", reply=" + reply +
                ", type=" + type +
                ", userID=" + userID +
                '}';
    }
}
