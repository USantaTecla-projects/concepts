package com.example.backend.api.resources.exam.module.answer.model.specific;

import com.example.backend.api.resources.exam.module.answer.model.Answer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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


    @Override
    public String toString() {
        return "AnswerT2{" +
                "reply=" + reply +
                '}';
    }
}
