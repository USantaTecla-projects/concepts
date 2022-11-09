package com.example.backend.api.resources.exam.domain.family.question.service;

import com.example.backend.api.resources.exam.domain.family.question.dto.QuestionDTO;
import com.example.backend.api.resources.exam.domain.family.question.exception.specific.QuestionDTOBadRequestException;
import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.tools.mapper.QuestionMapper;
import com.example.backend.api.resources.exam.domain.factory.Type;
import com.example.backend.api.resources.exam.domain.factory.TypeFactory;
import com.example.backend.api.resources.exam.domain.factory.TypeFactoryProvider;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MapQuestionService {

    private final List<TypeFactory> typeAbstractFactories;

    public MapQuestionService(TypeFactoryProvider typeFactoryProvider) {
        this.typeAbstractFactories = typeFactoryProvider.getTypeAbstractFactories();
    }

    public List<Question> mapQuestionDTOToQuestion(List<QuestionDTO> questionDTOList) {
        return questionDTOList
                .stream()
                .map(questionDTO -> {
                    final Type questionType = getQuestionType(questionDTO);
                    final int questionTypeOrdinal = questionType.ordinal();
                    QuestionMapper questionMapper = typeAbstractFactories.get(questionTypeOrdinal).createQuestionMapper();
                    return questionMapper.mapQuestion(questionDTO);
                })
                .toList();
    }

    private Type getQuestionType(QuestionDTO questionDTO) {
        return questionDTO
                .getTypeOptional(questionDTO.getType())
                .orElseThrow(() -> new QuestionDTOBadRequestException("Field type in QuestionDTO is mandatory"));
    }
}
