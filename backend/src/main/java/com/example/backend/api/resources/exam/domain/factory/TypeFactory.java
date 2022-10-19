package com.example.backend.api.resources.exam.domain.factory;

import com.example.backend.api.resources.exam.domain.family.answer.mapper.AnswerMapper;
import com.example.backend.api.resources.exam.domain.family.answer.service.type.SaveAnswerTypeService;
import com.example.backend.api.resources.exam.domain.family.question.tools.generator.QuestionGenerator;
import com.example.backend.api.resources.exam.domain.family.question.tools.mapper.QuestionMapper;
import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.service.saver.type.SaveQuestionTypeService;

public interface TypeFactory {

    QuestionGenerator createFiller();

    long getNumberOfAvailableQuestions();

    QuestionMapper createQuestionMapper();

    AnswerMapper createAnswerMapper();

    SaveAnswerTypeService getAnswerTypeService();

    SaveQuestionTypeService getQuestionTypeService();
}
