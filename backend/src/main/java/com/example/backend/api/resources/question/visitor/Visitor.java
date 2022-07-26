package com.example.backend.api.resources.question.visitor;

import com.example.backend.api.resources.question.models.QuestionT0;
import com.example.backend.api.resources.question.models.QuestionT1;
import com.example.backend.api.resources.question.models.QuestionT2;


public interface Visitor {

    void visitQuestionT0(QuestionT0 questionT0);

    void visitQuestionT1(QuestionT1 questionT1);

    void visitQuestionT2(QuestionT2 questionT2);

}
