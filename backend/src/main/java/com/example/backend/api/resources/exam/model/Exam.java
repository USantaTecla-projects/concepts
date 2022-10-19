package com.example.backend.api.resources.exam.model;


import com.example.backend.api.resources.exam.module.question.model.Question;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userID;

    private Timestamp creationDate;

    private Timestamp replyDate;

    private Long timeSpent;

    @JsonProperty("questionDataList")
    @ManyToMany
    private List<Question> questionList;


    public Exam() {
    }

    public Exam(final List<Question> questionList, final Long userID, final Timestamp creationDate) {
        this.questionList = questionList;
        this.userID = userID;
        this.creationDate = creationDate;
    }

    public void calculateTimeSpend(){
        if(replyDate == null){
            throw new RuntimeException("Reply date is mandatory to calculate the time spent, the exam isn't replied");
        }

        timeSpent = replyDate.getTime() - creationDate.getTime();
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

    public Long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Long timeSpent) {
        this.timeSpent = timeSpent;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }


    @Override
    public String toString() {
        return "Exam{" +
                "id=" + id +
                ", userID=" + userID +
                ", creationDate=" + creationDate +
                ", replyDate=" + replyDate +
                ", questionList=" + questionList +
                '}';
    }


}
