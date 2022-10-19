package com.example.backend.api.resources.exam.domain.family.question.service.saver;

import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.service.saver.type.SaveQuestionTypeService;
import com.example.backend.api.resources.exam.domain.factory.Type;
import com.example.backend.api.resources.exam.domain.factory.TypeFactory;
import com.example.backend.api.resources.exam.domain.factory.TypeFactoryProvider;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SaveQuestionService {

    private final List<TypeFactory> typeAbstractFactories;

    public SaveQuestionService(TypeFactoryProvider typeFactoryProvider) {
        this.typeAbstractFactories = typeFactoryProvider.getTypeAbstractFactories();
    }

    public void saveQuestion(Question question){
        final Type questionType = question.getType();
        final int questionTypeOrdinal = questionType.ordinal();
        SaveQuestionTypeService saveQuestionTypeService = typeAbstractFactories.get(questionTypeOrdinal).getQuestionTypeService();
        saveQuestionTypeService.saveQuestion(question);
    }

}
