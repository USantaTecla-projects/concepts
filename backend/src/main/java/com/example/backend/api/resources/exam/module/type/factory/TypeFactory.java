package com.example.backend.api.resources.exam.module.type.factory;

import com.example.backend.api.resources.exam.module.answer.mapper.AnswerMapper;
import com.example.backend.api.resources.exam.module.answer.service.type.AnswerTypeService;
import com.example.backend.api.resources.exam.module.question.filler.QuestionFiller;
import com.example.backend.api.resources.exam.module.question.mapper.QuestionMapper;
import com.example.backend.api.resources.exam.module.question.model.Question;
import com.example.backend.api.resources.exam.module.question.service.type.QuestionTypeService;
import com.example.backend.api.resources.exam.module.type.TypeData;

public interface TypeFactory {

    Question createQuestion();

    QuestionFiller createFiller();

    TypeData createNumberOfAvailableQuestions();

    QuestionMapper createQuestionMapper();

    AnswerMapper createAnswerMapper();

    AnswerTypeService getAnswerTypeService();

    QuestionTypeService getQuestionTypeService();
}
