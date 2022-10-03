package com.example.backend.api.resources.exam.module.question.service.type.specific;

import com.example.backend.api.resources.exam.module.question.model.Question;
import com.example.backend.api.resources.exam.module.question.model.specific.QuestionT2;
import com.example.backend.api.resources.exam.module.question.model.specific.QuestionT3;
import com.example.backend.api.resources.exam.module.question.repository.QuestionT2Repository;
import com.example.backend.api.resources.exam.module.question.repository.QuestionT3Repository;
import com.example.backend.api.resources.exam.module.question.service.type.QuestionTypeService;

public class QuestionT3Service implements QuestionTypeService {

    private final QuestionT3Repository questionT3Repository;


    public QuestionT3Service(QuestionT3Repository questionT3Repository) {
        this.questionT3Repository = questionT3Repository;
    }

    @Override
    public void saveQuestion(final Question question) {
        questionT3Repository.save((QuestionT3) question);
    }
}
