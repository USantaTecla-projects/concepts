package com.example.backend.api.resources.exam.domain.family.answer.service.type.specific;

import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.api.resources.exam.domain.family.answer.model.specific.AnswerT3;
import com.example.backend.api.resources.exam.domain.family.answer.repository.AnswerT3Repository;
import com.example.backend.api.resources.exam.domain.family.answer.service.type.SaveAnswerTypeService;
import org.springframework.stereotype.Service;

@Service
public class SaveAnswerT3Service implements SaveAnswerTypeService {

    private final AnswerT3Repository answerT3Repository;

    public SaveAnswerT3Service(AnswerT3Repository answerT3Repository) {
        this.answerT3Repository = answerT3Repository;
    }


    @Override
    public void saveAnswer(final Answer answer) {
        answerT3Repository.save((AnswerT3) answer);
    }
}
