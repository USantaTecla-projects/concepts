package com.example.backend.api.resources.exam.module.answer.model;

import com.example.backend.api.resources.exam.module.type.Type;

public class Answer {
    protected Type type;

    protected Long userID;

    public Answer() {
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


}
