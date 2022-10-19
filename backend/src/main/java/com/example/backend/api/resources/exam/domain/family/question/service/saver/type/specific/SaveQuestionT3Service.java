package com.example.backend.api.resources.exam.domain.family.question.service.saver.type.specific;

import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.model.specific.QuestionT3;
import com.example.backend.api.resources.exam.domain.family.question.service.saver.type.SaveQuestionTypeService;
import com.example.backend.api.resources.exam.domain.family.question.repository.QuestionT3Repository;

public class SaveQuestionT3Service implements SaveQuestionTypeService {

    private final QuestionT3Repository questionT3Repository;


    public SaveQuestionT3Service(QuestionT3Repository questionT3Repository) {
        this.questionT3Repository = questionT3Repository;
    }

    @Override
    public void saveQuestion(final Question question) {
        questionT3Repository.save((QuestionT3) question);
    }
}
