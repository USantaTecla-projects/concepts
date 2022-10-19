package com.example.backend.api.resources.exam.domain.family.answer.service.type.specific;

import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.api.resources.exam.domain.family.answer.model.specific.AnswerT1;
import com.example.backend.api.resources.exam.domain.family.answer.repository.AnswerT1Repository;
import com.example.backend.api.resources.exam.domain.family.answer.service.type.SaveAnswerTypeService;
import org.springframework.stereotype.Service;

@Service
public class SaveAnswerT1Service implements SaveAnswerTypeService {

    private final AnswerT1Repository answerT1Repository;

    public SaveAnswerT1Service(AnswerT1Repository answerT1Repository) {
        this.answerT1Repository = answerT1Repository;
    }

    @Override
    public void saveAnswer(final Answer answer) {
        answerT1Repository.save((AnswerT1) answer);
    }
}
