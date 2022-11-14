package com.example.backend.api.resources.exam.domain.family.answer.tools.saver.type.specific;

import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.api.resources.exam.domain.family.answer.model.specific.AnswerT3;
import com.example.backend.api.resources.exam.domain.family.answer.repository.AnswerT3Repository;
import com.example.backend.api.resources.exam.domain.family.answer.tools.saver.type.AnswerTypeSaver;
import org.springframework.stereotype.Service;

@Service
public class AnswerT3Saver implements AnswerTypeSaver {

    private final AnswerT3Repository answerT3Repository;

    public AnswerT3Saver(AnswerT3Repository answerT3Repository) {
        this.answerT3Repository = answerT3Repository;
    }


    @Override
    public void saveAnswer(final Answer answer) {
        answerT3Repository.save((AnswerT3) answer);
    }
}
