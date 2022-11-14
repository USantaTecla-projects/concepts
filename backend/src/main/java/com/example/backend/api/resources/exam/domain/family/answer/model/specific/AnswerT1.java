package com.example.backend.api.resources.exam.domain.family.answer.model.specific;

import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.model.specific.QuestionT1;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class AnswerT1 extends Answer {

    private String reply;

    @ManyToOne
    @JoinColumn(name = "question_t1_id")
    private QuestionT1 questionT1;

    public AnswerT1() {
    }

    @Override
    public void setQuestion(Question question) {
        QuestionT1 questionT1 = (QuestionT1) question;
        this.setQuestionT1(questionT1);
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public QuestionT1 getQuestionT1() {
        return questionT1;
    }

    public void setQuestionT1(QuestionT1 questionT1) {
        this.questionT1 = questionT1;
    }
}
