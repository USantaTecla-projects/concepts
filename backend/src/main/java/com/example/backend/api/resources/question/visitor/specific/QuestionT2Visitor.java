package com.example.backend.api.resources.question.visitor.specific;

import com.example.backend.api.resources.knowledge.answer.AnswerRepository;
import com.example.backend.api.resources.knowledge.answer.model.Answer;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.exception.model.ConceptNotFoundException;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import com.example.backend.api.resources.question.models.Question;
import com.example.backend.api.resources.question.models.QuestionType;
import com.example.backend.api.resources.question.models.specific.QuestionT2;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class QuestionT2Visitor {

    public final ConceptRepository conceptRepository;
    public final AnswerRepository answerRepository;

    public QuestionT2Visitor(ConceptRepository conceptRepository, AnswerRepository answerRepository) {
        this.conceptRepository = conceptRepository;
        this.answerRepository = answerRepository;
    }


    public void fillQuestionT2(final QuestionT2 questionT2, int randomNum, Map<QuestionType, List<Question>> questionReferences) {
        List<Question> type2Questions = questionReferences.get(QuestionType.TYPE2);
        List<Long> usedAnswerIds = new LinkedList<>(List.of(-1L));
        if (!type2Questions.isEmpty()) {
            usedAnswerIds = type2Questions.stream().map(question -> {
                QuestionT2 qT2 = (QuestionT2) question;
                return qT2.getAnswerId();
            }).toList();
        }

        final Answer answer = answerRepository.findRandomAnswer(usedAnswerIds, randomNum);

        final long conceptId = answer.getConceptId();
        final Concept concept = conceptRepository
                .findById(conceptId)
                .orElseThrow(() -> new ConceptNotFoundException("The concept with id = " + conceptId + " has not been found"));

        final String conceptText = concept.getText();
        final String answerText = answer.getText();
        final long answerId = answer.getId();

        questionT2.setType(QuestionType.TYPE2);
        questionT2.setConceptText(conceptText);
        questionT2.setConceptId(conceptId);
        questionT2.setAnswerText(answerText);
        questionT2.setAnswerId(answerId);
        questionT2.setQuestionAsString(questionT2.questionAsString());
    }
}
