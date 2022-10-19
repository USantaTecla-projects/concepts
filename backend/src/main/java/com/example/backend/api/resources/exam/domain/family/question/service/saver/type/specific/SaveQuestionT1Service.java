package com.example.backend.api.resources.exam.domain.family.question.service.saver.type.specific;

import com.example.backend.api.resources.exam.domain.family.question.repository.QuestionT1Repository;
import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.model.specific.QuestionT1;
import com.example.backend.api.resources.exam.domain.family.question.service.saver.type.SaveQuestionTypeService;

public class SaveQuestionT1Service implements SaveQuestionTypeService {

    private final QuestionT1Repository questionT1Repository;


    public SaveQuestionT1Service(QuestionT1Repository questionT1Repository) {
        this.questionT1Repository = questionT1Repository;
    }

    @Override
    public void saveQuestion(final Question question) {
        questionT1Repository.save((QuestionT1) question);
    }
}
