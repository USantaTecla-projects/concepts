package com.example.backend.api.resources.exam.module.answer.service;

import com.example.backend.api.resources.exam.module.answer.dto.AnswerDTO;
import com.example.backend.api.resources.exam.module.answer.mapper.AnswerMapper;
import com.example.backend.api.resources.exam.module.answer.model.Answer;
import com.example.backend.api.resources.exam.module.answer.service.type.AnswerTypeService;
import com.example.backend.api.resources.exam.module.question.exception.specific.QuestionDTOBadRequestException;
import com.example.backend.api.resources.exam.module.type.Type;
import com.example.backend.api.resources.exam.module.type.factory.TypeFactory;
import com.example.backend.api.resources.exam.module.type.factory.TypeFactoryProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    private final List<TypeFactory> typeAbstractFactories;

    public AnswerService(TypeFactoryProvider typeFactoryProvider) {
        this.typeAbstractFactories = typeFactoryProvider.getTypeAbstractFactories();
    }


    public List<Answer> saveAndGetAnswers(List<AnswerDTO> answerDTOList) {
        return answerDTOList
                .stream()
                .map(answerDTO -> {
                    Type answerType = getAnswerType(answerDTO);
                    Answer answer = mapAnswer(answerDTO, answerType);
                    saveAnswerOnDatabase(answerType, answer);
                    return answer;
                })
                .toList();

    }

    private Type getAnswerType(AnswerDTO answerDTO) {
        return answerDTO
                .getTypeOptional(answerDTO.getType())
                .orElseThrow(() -> new QuestionDTOBadRequestException("Field type in AnswerDTO is mandatory"));
    }

    private void saveAnswerOnDatabase(Type answerType, Answer answer) {
        AnswerTypeService answerTypeService = typeAbstractFactories.get(answerType.ordinal()).getAnswerTypeService();
        answerTypeService.saveAnswer(answer);
    }

    private Answer mapAnswer(AnswerDTO answerDTO, Type answerType) {
        AnswerMapper answerMapper = typeAbstractFactories.get(answerType.ordinal()).createAnswerMapper();
        return answerMapper.mapAnswer(answerDTO);
    }
}
