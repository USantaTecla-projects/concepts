package com.example.backend.api.resources.exam.module.answer;

import com.example.backend.api.resources.exam.module.answer.dto.AnswerDTO;
import com.example.backend.api.resources.exam.module.answer.mapper.AnswerMapper;
import com.example.backend.api.resources.exam.module.answer.model.Answer;
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


    public List<Answer> saveAndGetAnswers(List<AnswerDTO> answerDTOList){
        return answerDTOList
                .stream()
                .map(answerDTO -> {
                    Type answerType = answerDTO
                            .getTypeOptional(answerDTO.getType())
                            .orElseThrow(() -> new QuestionDTOBadRequestException("Field type in AnswerDTO is mandatory"));

                    AnswerMapper answerMapper = typeAbstractFactories.get(answerType.ordinal()).createAnswerMapper();
                    Answer answer = answerMapper.mapAnswer(answerDTO);

                    // TODO: Save answer in database

                    return answer;
                })
                .toList();

    }
}
