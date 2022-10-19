package com.example.backend.api.resources.exam.domain.family.question.service.saver.type.specific;

import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.model.specific.QuestionT2;
import com.example.backend.api.resources.exam.domain.family.question.repository.QuestionT2Repository;
import com.example.backend.api.resources.exam.domain.family.question.service.saver.type.SaveQuestionTypeService;

public class SaveQuestionT2Service implements SaveQuestionTypeService {

    private final QuestionT2Repository questionT2Repository;


    public SaveQuestionT2Service(QuestionT2Repository questionT2Repository) {
        this.questionT2Repository = questionT2Repository;
    }

    @Override
    public void saveQuestion(final Question question) {
        questionT2Repository.save((QuestionT2) question);
    }
}
