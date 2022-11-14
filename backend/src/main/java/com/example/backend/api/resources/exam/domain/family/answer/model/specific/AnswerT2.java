package com.example.backend.api.resources.exam.domain.family.answer.model.specific;

import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.model.specific.QuestionT2;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class AnswerT2 extends Answer {

    private Boolean reply;

    @ManyToOne
    @JoinColumn(name = "question_t2_id")
    private QuestionT2 questionT2;

    public AnswerT2() {
    }

    @Override
    public void setQuestion(Question question) {
        QuestionT2 questionT2 = (QuestionT2) question;
        this.setQuestionT2(questionT2);
    }


    public Boolean getReply() {
        return reply;
    }

    public void setReply(Boolean reply) {
        this.reply = reply;
    }

    public QuestionT2 getQuestionT2() {
        return questionT2;
    }

    public void setQuestionT2(QuestionT2 questionT2) {
        this.questionT2 = questionT2;
    }
}
