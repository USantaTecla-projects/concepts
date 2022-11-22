package com.example.backend.api.resources.exam.domain.family.answer.tools.saver.specific;

import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.api.resources.exam.domain.family.answer.model.specific.AnswerT1;
import com.example.backend.api.resources.exam.domain.family.answer.tools.saver.AnswerSaver;
import com.example.backend.api.resources.exam.domain.family.answer.repository.AnswerT1Repository;
import org.springframework.stereotype.Service;

@Service
public class AnswerT1Saver implements AnswerSaver {

    private final AnswerT1Repository answerT1Repository;

    public AnswerT1Saver(AnswerT1Repository answerT1Repository) {
        this.answerT1Repository = answerT1Repository;
    }

    @Override
    public void saveAnswer(final Answer answer) {
        answerT1Repository.save((AnswerT1) answer);
    }
}
