package com.example.backend.api.resources.exam.module.answer.service.type.specific;

import com.example.backend.api.resources.exam.module.answer.model.Answer;
import com.example.backend.api.resources.exam.module.answer.model.specific.AnswerT1;
import com.example.backend.api.resources.exam.module.answer.repository.AnswerT1Repository;
import com.example.backend.api.resources.exam.module.answer.service.type.AnswerTypeService;
import org.springframework.stereotype.Service;

@Service
public class AnswerT1Service implements AnswerTypeService {

    private final AnswerT1Repository answerT1Repository;

    public AnswerT1Service(AnswerT1Repository answerT1Repository) {
        this.answerT1Repository = answerT1Repository;
    }

    @Override
    public void saveAnswer(final Answer answer) {
        answerT1Repository.save((AnswerT1) answer);
    }
}
