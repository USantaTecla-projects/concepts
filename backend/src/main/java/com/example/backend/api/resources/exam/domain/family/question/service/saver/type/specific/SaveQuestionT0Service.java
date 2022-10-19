package com.example.backend.api.resources.exam.domain.family.question.service.saver.type.specific;

import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.repository.QuestionT0Repository;
import com.example.backend.api.resources.exam.domain.family.question.service.saver.type.SaveQuestionTypeService;
import com.example.backend.api.resources.exam.domain.family.question.model.specific.QuestionT0;

public class SaveQuestionT0Service implements SaveQuestionTypeService {

    private final QuestionT0Repository questionT0Repository;

    public SaveQuestionT0Service(QuestionT0Repository questionT0Repository) {
        this.questionT0Repository = questionT0Repository;
    }
    @Override
    public void saveQuestion(final Question question) {
        questionT0Repository.save((QuestionT0) question);
    }
}
