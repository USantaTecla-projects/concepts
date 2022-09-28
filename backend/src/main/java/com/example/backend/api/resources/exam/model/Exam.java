package com.example.backend.api.resources.exam.model;


import com.example.backend.api.resources.exam.resources.question.model.Question;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Exam {
    @JsonProperty("questionDataList")
    private List<Question> questionList;

    public Exam(List<Question> questionList) {
        this.questionList = questionList;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
}
