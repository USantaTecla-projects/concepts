package com.example.backend.api.resources.question.visitor.specific;

import com.example.backend.api.resources.knowledge.answer.AnswerRepository;
import com.example.backend.api.resources.knowledge.answer.model.Answer;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.exception.model.ConceptNotFoundException;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import com.example.backend.api.resources.question.models.Question;
import com.example.backend.api.resources.question.models.QuestionType;
import com.example.backend.api.resources.question.models.specific.QuestionT1;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class QuestionT1Visitor {

    public final ConceptRepository conceptRepository;
    public final AnswerRepository answerRepository;

    public QuestionT1Visitor(ConceptRepository conceptRepository, AnswerRepository answerRepository) {
        this.conceptRepository = conceptRepository;
        this.answerRepository = answerRepository;
    }

    public void fillQuestionT1(final QuestionT1 questionT1, final int randomNum, Map<QuestionType, List<Question>> questionReferences) {
        List<Question> type1Questions = questionReferences.get(QuestionType.TYPE1);
        List<Long> usedIncorrectAnswerIds = new LinkedList<>(List.of(-1L));
        if (!type1Questions.isEmpty()) {
            usedIncorrectAnswerIds = type1Questions.stream().map(question -> {
                QuestionT1 qT1 = (QuestionT1) question;
                return qT1.getAnswerId();
            }).toList();
        }

        final Answer answer = answerRepository.findRandomAnswerBasedOnCorrect(usedIncorrectAnswerIds, false, randomNum);

        final long conceptId = answer.getConceptId();
        final Concept concept = conceptRepository
                .findById(conceptId)
                .orElseThrow(() -> new ConceptNotFoundException("The concept with id = " + conceptId + " has not been found"));

        final String conceptText = concept.getText();
        final String incorrectAnswerText = answer.getText();
        final long answerId = answer.getId();

        questionT1.setType(QuestionType.TYPE1);
        questionT1.setConceptText(conceptText);
        questionT1.setConceptId(conceptId);
        questionT1.setIncorrectAnswerText(incorrectAnswerText);
        questionT1.setAnswerId(answerId);
        questionT1.setQuestionAsString(questionT1.questionAsString());
    }
}
