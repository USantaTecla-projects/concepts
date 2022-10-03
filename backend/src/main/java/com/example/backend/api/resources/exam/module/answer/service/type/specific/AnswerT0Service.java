package com.example.backend.api.resources.exam.module.answer.service.type.specific;

import com.example.backend.api.resources.exam.module.answer.model.Answer;
import com.example.backend.api.resources.exam.module.answer.model.specific.AnswerT0;
import com.example.backend.api.resources.exam.module.answer.repository.AnswerT0Repository;
import com.example.backend.api.resources.exam.module.answer.service.type.AnswerTypeService;
import org.springframework.stereotype.Service;

@Service
public class AnswerT0Service implements AnswerTypeService {


    private final AnswerT0Repository answerT0Repository;

    public AnswerT0Service(AnswerT0Repository answerT0Repository) {
        this.answerT0Repository = answerT0Repository;
    }

    @Override
    public void saveAnswer(final Answer answer) {
        answerT0Repository.save((AnswerT0) answer);
    }
}
