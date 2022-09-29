package com.example.backend.api.resources.exam.module.answer.model.specific;

import com.example.backend.api.resources.exam.module.answer.model.Answer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AnswerT0 extends Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String reply;

    public AnswerT0() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    @Override
    public String toString() {
        return "AnswerT0{" +
                "id=" + id +
                ", reply='" + reply + '\'' +
                ", type=" + type +
                ", userID=" + userID +
                '}';
    }
}
