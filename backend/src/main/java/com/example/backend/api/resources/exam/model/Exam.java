package com.example.backend.api.resources.exam.model;


import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.user.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userID;

    private Timestamp creationDate;

    private Timestamp replyDate;

    private Boolean corrected = false;

    private String mark;
    @ManyToMany
    private List<Question> questionList;

    @OneToMany
    private List<Answer> answerList;

    public Exam() {
    }

    public Exam(final List<Question> questionList, final Long userID, final Timestamp creationDate) {
        this.questionList = questionList;
        this.userID = userID;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Timestamp replyDate) {
        this.replyDate = replyDate;
    }

    public Boolean getCorrected() {
        return corrected;
    }

    public void setCorrected(Boolean corrected) {
        this.corrected = corrected;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }
}
