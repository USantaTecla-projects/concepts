package com.example.backend.api.core.data;

import com.example.backend.api.core.answer.dto.AnswerDTO;
import com.example.backend.api.core.answer.model.Answer;

public final class AnswerTestDataConstants {

    private AnswerTestDataConstants() {
    }


    // Simple Answer DTOs
    public static final AnswerDTO answerDTO1 = new AnswerDTO("Software answer", true);
    public static final AnswerDTO answerDTO2 = new AnswerDTO("Hardware answer", true);
    public static final AnswerDTO answerDTO3 = new AnswerDTO("Functional Programming answer", false);
    public static final AnswerDTO answerDTO4 = new AnswerDTO("Unix answer", false);
    public static final AnswerDTO answerDTO5 = new AnswerDTO("Haskell answer", true);

    // Wrong Answer DTOs
    public static final AnswerDTO wrongAnswerDTO1 = new AnswerDTO("");
    public static final AnswerDTO wrongAnswerDTO2 = new AnswerDTO("");

    // Answers
    public static final Answer answer1 = new Answer(2L,"Software answer", true, 1L);
    public static final Answer answer2 = new Answer(4L,"Hardware answer", true, 3L);

}
