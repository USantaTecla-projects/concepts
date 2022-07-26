package com.example.backend.api.resources.question.visitor;

import com.example.backend.api.resources.question.models.QuestionT0;
import com.example.backend.api.resources.question.models.QuestionT1;
import com.example.backend.api.resources.question.models.QuestionT2;
import org.springframework.stereotype.Component;

@Component
public class QuestionVisitor implements Visitor{

    //On these methods I will fill up the data of the questions
    @Override
    public void visitQuestionT0(QuestionT0 questionT0) {

    }

    @Override
    public void visitQuestionT1(QuestionT1 questionT1) {

    }

    @Override
    public void visitQuestionT2(QuestionT2 questionT2) {

    }
}
