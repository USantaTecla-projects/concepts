package com.example.backend.api.resources.exam.module.answer.service.type.specific;

import com.example.backend.api.resources.exam.module.answer.model.Answer;
import com.example.backend.api.resources.exam.module.answer.model.specific.AnswerT3;
import com.example.backend.api.resources.exam.module.answer.repository.AnswerT3Repository;
import com.example.backend.api.resources.exam.module.answer.service.type.AnswerTypeService;
import org.springframework.stereotype.Service;

@Service
public class AnswerT3Service implements AnswerTypeService {

    private final AnswerT3Repository answerT3Repository;

    public AnswerT3Service(AnswerT3Repository answerT3Repository) {
        this.answerT3Repository = answerT3Repository;
    }


    @Override
    public void saveAnswer(final Answer answer) {
        answerT3Repository.save((AnswerT3) answer);
    }
}
