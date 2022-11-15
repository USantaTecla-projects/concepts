package com.example.backend.api.resources.exam.domain.family.answer.model.specific;

import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;

import javax.persistence.Entity;

@Entity
public class AnswerT1 extends Answer {

    private String reply;

    public AnswerT1() {
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

}
