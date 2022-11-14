package com.example.backend.api.resources.exam.domain.family.answer.model.specific;

import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.model.specific.QuestionT0;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class AnswerT0 extends Answer {

    private String reply;

    @ManyToOne
    @JoinColumn(name = "question_t0_id")
    private QuestionT0 questionT0;

    public AnswerT0() {
    }

    @Override
    public void setQuestion(Question question) {
        QuestionT0 questionT0 = (QuestionT0) question;
        this.setQuestionT0(questionT0);
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public QuestionT0 getQuestionT0() {
        return questionT0;
    }

    public void setQuestionT0(QuestionT0 questionT0) {
        this.questionT0 = questionT0;
    }


}
