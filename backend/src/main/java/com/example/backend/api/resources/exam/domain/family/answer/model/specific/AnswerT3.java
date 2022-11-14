package com.example.backend.api.resources.exam.domain.family.answer.model.specific;

import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.model.specific.QuestionT3;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class AnswerT3 extends Answer {

    private Boolean reply;

    @ManyToOne
    @JoinColumn(name = "question_t3_id")
    private QuestionT3 questionT3;

    public AnswerT3() {
    }

    @Override
    public void setQuestion(Question question) {
        QuestionT3 questionT3 = (QuestionT3) question;
        this.setQuestionT3(questionT3);
    }


    public Boolean getReply() {
        return reply;
    }

    public void setReply(Boolean reply) {
        this.reply = reply;
    }

    public QuestionT3 getQuestionT3() {
        return questionT3;
    }

    public void setQuestionT3(QuestionT3 questionT3) {
        this.questionT3 = questionT3;
    }
}
