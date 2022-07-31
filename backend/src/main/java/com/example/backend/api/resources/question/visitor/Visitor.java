package com.example.backend.api.resources.question.visitor;

import com.example.backend.api.resources.question.models.*;
import com.example.backend.api.resources.question.models.specific.QuestionT0;
import com.example.backend.api.resources.question.models.specific.QuestionT1;
import com.example.backend.api.resources.question.models.specific.QuestionT2;
import com.example.backend.api.resources.question.models.specific.QuestionT3;

import java.util.List;
import java.util.Map;


public interface Visitor {

    void generateQuestionT0(QuestionT0 questionT0, Map<QuestionType, List<Question>> questionReferences);

    void generateQuestionT1(QuestionT1 questionT1, Map<QuestionType, List<Question>> questionReferences);

    void generateQuestionT2(QuestionT2 questionT2, Map<QuestionType, List<Question>> questionReferences);

    void generateQuestionT3(QuestionT3 questionT3, Map<QuestionType, List<Question>> questionReferences);
}
