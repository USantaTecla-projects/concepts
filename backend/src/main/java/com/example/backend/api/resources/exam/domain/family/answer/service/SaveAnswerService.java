package com.example.backend.api.resources.exam.domain.family.answer.service;

import com.example.backend.api.resources.exam.domain.family.answer.dto.AnswerDTO;
import com.example.backend.api.resources.exam.domain.family.answer.mapper.AnswerMapper;
import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.api.resources.exam.domain.family.answer.service.type.SaveAnswerTypeService;
import com.example.backend.api.resources.exam.domain.family.question.exception.specific.QuestionDTOBadRequestException;
import com.example.backend.api.resources.exam.domain.factory.Type;
import com.example.backend.api.resources.exam.domain.factory.TypeFactory;
import com.example.backend.api.resources.exam.domain.factory.TypeFactoryProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaveAnswerService {

    private final List<TypeFactory> typeAbstractFactories;

    public SaveAnswerService(TypeFactoryProvider typeFactoryProvider) {
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

    private Answer mapAnswer(AnswerDTO answerDTO, Type answerType) {
        AnswerMapper answerMapper = typeAbstractFactories.get(answerType.ordinal()).createAnswerMapper();
        return answerMapper.mapAnswer(answerDTO);
    }

    private void saveAnswerOnDatabase(Type answerType, Answer answer) {
        SaveAnswerTypeService saveAnswerTypeService = typeAbstractFactories.get(answerType.ordinal()).getAnswerTypeService();
        saveAnswerTypeService.saveAnswer(answer);
    }
}
