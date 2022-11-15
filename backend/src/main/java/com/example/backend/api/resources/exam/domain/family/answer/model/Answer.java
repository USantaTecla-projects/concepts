package com.example.backend.api.resources.exam.domain.family.answer.model;

import com.example.backend.api.resources.exam.domain.factory.Type;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    protected Type type;

    protected Long userID;

    protected Long questionID;

    protected CorrectionStatus correctionStatus = CorrectionStatus.PENDING;

    public Answer() {
    }

    public void setQuestionId(Long questionID) {
        this.questionID = questionID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Long questionID) {
        this.questionID = questionID;
    }

    public CorrectionStatus getCorrectionStatus() {
        return correctionStatus;
    }

    public void setCorrectionStatus(CorrectionStatus correctionStatus) {
        this.correctionStatus = correctionStatus;
    }
}
