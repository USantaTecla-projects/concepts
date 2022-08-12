package com.example.backend.api.resources.exam.question.filler.specific;

import com.example.backend.api.resources.knowledge.answer.AnswerRepository;
import com.example.backend.api.resources.knowledge.answer.exception.model.AnswerNotFoundException;
import com.example.backend.api.resources.knowledge.answer.model.Answer;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.exception.model.ConceptNotFoundException;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import com.example.backend.api.resources.knowledge.justification.JustificationRepository;
import com.example.backend.api.resources.knowledge.justification.exception.model.JustificationNotFoundException;
import com.example.backend.api.resources.knowledge.justification.model.Justification;
import com.example.backend.api.resources.exam.question.filler.Filler;
import com.example.backend.api.resources.exam.question.model.Question;
import com.example.backend.api.resources.exam.question.model.specific.QuestionT3;
import com.example.backend.api.resources.exam.type.QuestionType;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class QuestionT3Filler implements Filler {

    private final ConceptRepository conceptRepository;
    private final AnswerRepository answerRepository;
    private final JustificationRepository justificationRepository;

    public QuestionT3Filler(ConceptRepository conceptRepository, AnswerRepository answerRepository, JustificationRepository justificationRepository) {
        this.justificationRepository = justificationRepository;
        this.answerRepository = answerRepository;
        this.conceptRepository = conceptRepository;
    }

    @Override
    public void fillQuestion(final Question question, List<Question> questions) {
        final int randomNum = generateRandomNumber();

        QuestionT3 questionT3 = (QuestionT3) question;

        List<QuestionT3> questionT3List = findQuestionsT3(questions);
        List<Long> usedJustificationRelatedWithIncorrectAnswerIds = new LinkedList<>(List.of(-1L));
        if (!questionT3List.isEmpty()) {
            usedJustificationRelatedWithIncorrectAnswerIds = questionT3List.stream()
                    .map(QuestionT3::getJustificationId)
                    .toList();
        }

        final Justification justification = justificationRepository
                .findOneJustificationLinkedToIncorrectAnswer(usedJustificationRelatedWithIncorrectAnswerIds, randomNum)
                .orElseThrow(() -> {
                    questionT3.setFilled(false);
                    throw new JustificationNotFoundException("No justification was found, probably all justifications have been used in this type of question");
                });

        final long answerId = justification.getAnswerId();
        final long conceptId = justification.getConceptId();
        final long justificationId = justification.getId();

        final Answer incorrectAnswer = answerRepository
                .findById(answerId)
                .orElseThrow(() -> {
                    questionT3.setFilled(false);
                    throw new AnswerNotFoundException("The answer with id = " + answerId + " has not been found");
                });

        final Concept concept = conceptRepository
                .findById(conceptId)
                .orElseThrow(() -> {
                    questionT3.setFilled(false);
                    throw new ConceptNotFoundException("The concept with id = " + conceptId + " has not been found");
                });

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
        questionT3.setQuestionAsString(questionT3.questionAsString());
        question.setFilled(true);
    }

    private List<QuestionT3> findQuestionsT3(List<Question> questions) {
        return questions.stream()
                .filter(question -> question.getType() == QuestionType.TYPE3)
                .map(question -> (QuestionT3) question)
                .toList();
    }

    private int generateRandomNumber() {
        final long numberOfIncorrectAnswers = answerRepository.countAnswerByIsCorrect(false);
        final int MIN = 0;
        final int MAX = (int) numberOfIncorrectAnswers;
        return ThreadLocalRandom.current().nextInt(MIN, MAX);
    }


}
