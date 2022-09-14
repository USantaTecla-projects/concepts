package com.example.backend.api.resources.exam.model;


import com.example.backend.api.resources.exam.question.model.Question;

import java.util.List;

public class Exam {

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
