package com.example.backend.api.resources.exam.module.question.service.type.specific;

import com.example.backend.api.resources.exam.module.question.model.Question;
import com.example.backend.api.resources.exam.module.question.model.specific.QuestionT1;
import com.example.backend.api.resources.exam.module.question.model.specific.QuestionT2;
import com.example.backend.api.resources.exam.module.question.repository.QuestionT1Repository;
import com.example.backend.api.resources.exam.module.question.repository.QuestionT2Repository;
import com.example.backend.api.resources.exam.module.question.service.type.QuestionTypeService;

public class QuestionT2Service implements QuestionTypeService {

    private final QuestionT2Repository questionT2Repository;


    public QuestionT2Service(QuestionT2Repository questionT2Repository) {
        this.questionT2Repository = questionT2Repository;
    }

    @Override
    public void saveQuestion(final Question question) {
        questionT2Repository.save((QuestionT2) question);
    }
}
