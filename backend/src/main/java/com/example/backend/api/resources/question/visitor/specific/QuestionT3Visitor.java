package com.example.backend.api.resources.question.visitor.specific;

import com.example.backend.api.resources.knowledge.answer.AnswerRepository;
import com.example.backend.api.resources.knowledge.answer.exception.model.AnswerNotFoundException;
import com.example.backend.api.resources.knowledge.answer.model.Answer;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.exception.model.ConceptNotFoundException;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import com.example.backend.api.resources.knowledge.justification.JustificationRepository;
import com.example.backend.api.resources.knowledge.justification.model.Justification;
import com.example.backend.api.resources.question.models.Question;
import com.example.backend.api.resources.question.models.QuestionType;
import com.example.backend.api.resources.question.models.specific.QuestionT3;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class QuestionT3Visitor {

    private final ConceptRepository conceptRepository;
    private final AnswerRepository answerRepository;
    public final JustificationRepository justificationRepository;

    public QuestionT3Visitor(ConceptRepository conceptRepository, AnswerRepository answerRepository, JustificationRepository justificationRepository) {
        this.justificationRepository = justificationRepository;
        this.answerRepository = answerRepository;
        this.conceptRepository = conceptRepository;
    }

    public void fillQuestionT3(final QuestionT3 questionT3, int randomNum, Map<QuestionType, List<Question>> questionReferences) {
        List<Question> type3Questions = questionReferences.get(QuestionType.TYPE3);
        List<Long> usedJustificationRelatedWithIncorrectAnswerIds = new LinkedList<>(List.of(-1L));
        if (!type3Questions.isEmpty()) {
            usedJustificationRelatedWithIncorrectAnswerIds = type3Questions.stream().map(question -> {
                QuestionT3 qT3 = (QuestionT3) question;
                return qT3.getJustificationId();
            }).toList();
        }

        final Justification justification = justificationRepository
                .findOneJustificationLinkedToIncorrectAnswer(usedJustificationRelatedWithIncorrectAnswerIds, randomNum);


        final long answerId = justification.getAnswerId();
        final long conceptId = justification.getConceptId();
        final long justificationId = justification.getId();

        final Answer incorrectAnswer = answerRepository
                .findById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException("The answer with id = " + answerId + " has not been found"));

        final Concept concept = conceptRepository
                .findById(conceptId)
                .orElseThrow(() -> new ConceptNotFoundException("The concept with id = " + conceptId + " has not been found"));

        final String justificationText = justification.getText();
        final String incorrectAnswerText = incorrectAnswer.getText();
        final String conceptText = concept.getText();

        questionT3.setType(QuestionType.TYPE3);
        questionT3.setJustificationText(justificationText);
        questionT3.setJustificationId(justificationId);
        questionT3.setIncorrectAnswerText(incorrectAnswerText);
        questionT3.setAnswerId(answerId);
        questionT3.setConceptText(conceptText);
        questionT3.setConceptId(conceptId);
    }
}
