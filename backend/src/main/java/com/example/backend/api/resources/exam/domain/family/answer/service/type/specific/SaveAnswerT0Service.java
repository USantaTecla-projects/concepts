package com.example.backend.api.resources.exam.domain.family.answer.service.type.specific;

import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.api.resources.exam.domain.family.answer.model.specific.AnswerT0;
import com.example.backend.api.resources.exam.domain.family.answer.repository.AnswerT0Repository;
import com.example.backend.api.resources.exam.domain.family.answer.service.type.SaveAnswerTypeService;
import org.springframework.stereotype.Service;

@Service
public class SaveAnswerT0Service implements SaveAnswerTypeService {


    private final AnswerT0Repository answerT0Repository;

    public SaveAnswerT0Service(AnswerT0Repository answerT0Repository) {
        this.answerT0Repository = answerT0Repository;
    }

    @Override
    public void saveAnswer(final Answer answer) {
        answerT0Repository.save((AnswerT0) answer);
    }
}
