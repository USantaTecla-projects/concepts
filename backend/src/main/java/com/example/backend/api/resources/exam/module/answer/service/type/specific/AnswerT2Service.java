package com.example.backend.api.resources.exam.module.answer.service.type.specific;

import com.example.backend.api.resources.exam.module.answer.model.Answer;
import com.example.backend.api.resources.exam.module.answer.model.specific.AnswerT2;
import com.example.backend.api.resources.exam.module.answer.repository.AnswerT2Repository;
import com.example.backend.api.resources.exam.module.answer.service.type.AnswerTypeService;
import org.springframework.stereotype.Service;

@Service
public class AnswerT2Service implements AnswerTypeService {

    private final AnswerT2Repository answerT2Repository;

    public AnswerT2Service(AnswerT2Repository answerT2Repository) {
        this.answerT2Repository = answerT2Repository;
    }


    @Override
    public void saveAnswer(final Answer answer) {
        answerT2Repository.save((AnswerT2) answer);
    }
}
