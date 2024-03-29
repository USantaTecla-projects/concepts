package com.example.backend.api.resources.exam.domain.family.answer.tools.saver.specific;

import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.api.resources.exam.domain.family.answer.model.specific.AnswerT0;
import com.example.backend.api.resources.exam.domain.family.answer.tools.saver.AnswerSaver;
import com.example.backend.api.resources.exam.domain.family.answer.repository.AnswerT0Repository;
import org.springframework.stereotype.Service;

@Service
public class AnswerT0Saver implements AnswerSaver {


    private final AnswerT0Repository answerT0Repository;

    public AnswerT0Saver(AnswerT0Repository answerT0Repository) {
        this.answerT0Repository = answerT0Repository;
    }

    @Override
    public void saveAnswer(final Answer answer) {
        answerT0Repository.save((AnswerT0) answer);
    }
}
