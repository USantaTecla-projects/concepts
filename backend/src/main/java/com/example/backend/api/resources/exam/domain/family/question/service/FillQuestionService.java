package com.example.backend.api.resources.exam.domain.family.question.service;

import com.example.backend.api.resources.exam.domain.factory.Type;
import com.example.backend.api.resources.exam.domain.factory.TypeFactory;
import com.example.backend.api.resources.exam.domain.factory.TypeFactoryProvider;
import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.tools.filler.QuestionFiller;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FillQuestionService {

    private final List<TypeFactory> typeAbstractFactories;

    public FillQuestionService(TypeFactoryProvider typeFactoryProvider) {
        this.typeAbstractFactories = typeFactoryProvider.getTypeAbstractFactories();
    }

    public List<Question> fillQuestionList(List<Question> questionList) {
        return questionList
                .stream()
                .map((question) -> {
                    final Type questionType = question.getType();
                    final int questionTypeOrdinal = questionType.ordinal();
                    QuestionFiller questionFiller = typeAbstractFactories.get(questionTypeOrdinal).createQuestionFiller();
                    return questionFiller.fillQuestionWithData(question);
                })
                .toList();
    }
}
