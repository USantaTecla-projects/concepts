package com.example.backend.api.resources.exam.module.question.service.type.specific;

import com.example.backend.api.resources.exam.module.question.model.Question;
import com.example.backend.api.resources.exam.module.question.model.specific.QuestionT1;
import com.example.backend.api.resources.exam.module.question.repository.QuestionT1Repository;
import com.example.backend.api.resources.exam.module.question.service.type.QuestionTypeService;

public class QuestionT1Service implements QuestionTypeService {

    private final QuestionT1Repository questionT1Repository;


    public QuestionT1Service(QuestionT1Repository questionT1Repository) {
        this.questionT1Repository = questionT1Repository;
    }

    @Override
    public void saveQuestion(final Question question) {
        questionT1Repository.save((QuestionT1) question);
    }
}
