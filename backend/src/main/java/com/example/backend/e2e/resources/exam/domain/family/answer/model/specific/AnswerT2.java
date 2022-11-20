package com.example.backend.e2e.resources.exam.domain.family.answer.model.specific;

import com.example.backend.e2e.resources.exam.domain.family.answer.model.Answer;

import javax.persistence.Entity;

@Entity
public class AnswerT2 extends Answer {

    private Boolean reply;

    public AnswerT2() {
    }


    public Boolean getReply() {
        return reply;
    }

    public void setReply(Boolean reply) {
        this.reply = reply;
    }


}
