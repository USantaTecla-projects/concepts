package com.example.backend.api.resources.exam.module.question.service.type.specific;

import com.example.backend.api.resources.exam.module.question.model.Question;
import com.example.backend.api.resources.exam.module.question.model.specific.QuestionT0;
import com.example.backend.api.resources.exam.module.question.repository.QuestionT0Repository;
import com.example.backend.api.resources.exam.module.question.service.type.QuestionTypeService;

public class QuestionT0Service implements QuestionTypeService {

    private final QuestionT0Repository questionT0Repository;


    public QuestionT0Service(QuestionT0Repository questionT0Repository) {
        this.questionT0Repository = questionT0Repository;
    }

    @Override
    public void saveQuestion(final Question question) {
        questionT0Repository.save((QuestionT0) question);
    }
}
