package com.example.backend.api.resources.exam.domain.factory;

import com.example.backend.api.resources.exam.domain.family.question.tools.filler.QuestionFiller;
import com.example.backend.api.resources.exam.domain.family.answer.tools.saver.AnswerSaver;
import com.example.backend.api.resources.exam.domain.family.answer.tools.mapper.AnswerMapper;
import com.example.backend.api.resources.exam.domain.family.question.tools.generator.QuestionGenerator;
import com.example.backend.api.resources.exam.domain.family.question.tools.mapper.QuestionMapper;

public interface TypeFactory {

    QuestionGenerator createGenerator();

    long getNumberOfAvailableQuestions();

    QuestionMapper createQuestionMapper();

    AnswerMapper createAnswerMapper();

    AnswerSaver getAnswerTypeService();

    QuestionFiller createQuestionFiller();
}
