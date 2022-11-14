package com.example.backend.api.resources.exam.domain.family.answer.tools.saver.type.specific;

import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.api.resources.exam.domain.family.answer.model.specific.AnswerT2;
import com.example.backend.api.resources.exam.domain.family.answer.repository.AnswerT2Repository;
import com.example.backend.api.resources.exam.domain.family.answer.tools.saver.type.AnswerTypeSaver;
import org.springframework.stereotype.Service;

@Service
public class AnswerT2Saver implements AnswerTypeSaver {

    private final AnswerT2Repository answerT2Repository;

    public AnswerT2Saver(AnswerT2Repository answerT2Repository) {
        this.answerT2Repository = answerT2Repository;
    }


    @Override
    public void saveAnswer(final Answer answer) {
        answerT2Repository.save((AnswerT2) answer);
    }
}
