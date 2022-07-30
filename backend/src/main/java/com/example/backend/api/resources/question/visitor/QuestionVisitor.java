package com.example.backend.api.resources.question.visitor;

import com.example.backend.api.resources.knowledge.answer.AnswerRepository;
import com.example.backend.api.resources.knowledge.answer.exception.model.AnswerNotFoundException;
import com.example.backend.api.resources.knowledge.answer.model.Answer;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.exception.model.ConceptNotFoundException;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import com.example.backend.api.resources.knowledge.justification.JustificationRepository;
import com.example.backend.api.resources.knowledge.justification.model.Justification;
import com.example.backend.api.resources.question.exception.model.NotEnoughDataException;
import com.example.backend.api.resources.question.models.QuestionT0;
import com.example.backend.api.resources.question.models.QuestionT1;
import com.example.backend.api.resources.question.models.QuestionT2;
import com.example.backend.api.resources.question.models.QuestionT3;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class QuestionVisitor implements Visitor {


    public final ConceptRepository conceptRepository;
    public final AnswerRepository answerRepository;
    public final JustificationRepository justificationRepository;

    public QuestionVisitor(
            final ConceptRepository conceptRepository,
            final AnswerRepository answerRepository,
            JustificationRepository justificationRepository) {
        this.conceptRepository = conceptRepository;
        this.answerRepository = answerRepository;
        this.justificationRepository = justificationRepository;
    }

    //On these methods I will fill up the data of the questions
    @Override
    public void visitQuestionT0(final QuestionT0 questionT0) {
        final long numberOfConcepts = conceptRepository.count();
        if (numberOfConcepts == 0)
            throw new NotEnoughDataException("There are no concepts in database to create the question");

        final int randomNum = generateRandomNumber(numberOfConcepts);
        final Page<Concept> conceptPage = conceptRepository.findAll(PageRequest.of(randomNum, 1));
        final String conceptText = conceptPage
                .getContent()
                .get(0)
                .getText();

        questionT0.setType("Type 0");
        questionT0.setConceptText(conceptText);
    }

    @Override
    public void visitQuestionT1(final QuestionT1 questionT1) {
        final long numberOfIncorrectAnswers = answerRepository.countAnswerByIsCorrect(false);
        if (numberOfIncorrectAnswers == 0)
            throw new NotEnoughDataException("There are no incorrect answers in the database to create the question");

        final int randomNum = generateRandomNumber(numberOfIncorrectAnswers);
        final Page<Answer> answerPage = answerRepository.findAllByIsCorrect(PageRequest.of(randomNum, 1), false);
        final Answer answer = answerPage
                .getContent()
                .get(0);

        final long conceptId = answer.getConceptId();
        final Concept concept = conceptRepository
                .findById(conceptId)
                .orElseThrow(() -> new ConceptNotFoundException("The concept with id = " + conceptId + " has not been found"));

        final String conceptText = concept.getText();
        final String incorrectAnswerText = answer.getText();

        questionT1.setType("Type 1");
        questionT1.setConceptText(conceptText);
        questionT1.setIncorrectAnswerText(incorrectAnswerText);
    }

    @Override
    public void visitQuestionT2(final QuestionT2 questionT2) {
        final long numberOfAnswers = answerRepository.count();
        if (numberOfAnswers == 0)
            throw new NotEnoughDataException("There are no answers in database to create the question");

        final int randomNum = generateRandomNumber(numberOfAnswers);
        final Page<Answer> answerPage = answerRepository.findAll(PageRequest.of(randomNum, 1));
        final Answer answer = answerPage
                .getContent()
                .get(0);

        final long conceptId = answer.getConceptId();
        final Concept concept = conceptRepository
                .findById(conceptId)
                .orElseThrow(() -> new ConceptNotFoundException("The concept with id = " + conceptId + " has not been found"));

        final String conceptText = concept.getText();
        final String answerText = answer.getText();

        questionT2.setType("Type 2");
        questionT2.setConceptText(conceptText);
        questionT2.setAnswerText(answerText);
    }

    @Override
    public void visitQuestionT3(final QuestionT3 questionT3) {
        final long numberOfJustifications = justificationRepository.count();
        if (numberOfJustifications == 0)
            throw new NotEnoughDataException("There are no justification in database to create the question");

        final long numberOfIncorrectAnswers = answerRepository.countAnswerByIsCorrect(false);
        if (numberOfIncorrectAnswers == 0)
            throw new NotEnoughDataException("There are no incorrect answers in the database to create the question");

        final int randomNum = generateRandomNumber(numberOfIncorrectAnswers);
        Justification justification = justificationRepository.findOneJustificationLinkedToIncorrectAnswer(randomNum);

        long answerId = justification.getAnswerId();
        long conceptId = justification.getConceptId();

        Answer incorrectAnswer = answerRepository
                .findById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException("The answer with id = " + answerId + " has not been found"));

        Concept concept = conceptRepository
                .findById(conceptId)
                .orElseThrow(() -> new ConceptNotFoundException("The concept with id = " + conceptId + " has not been found"));

        String justificationText = justification.getText();
        String incorrectAnswerText = incorrectAnswer.getText();
        String conceptText = concept.getText();

        questionT3.setType("Type 3");
        questionT3.setJustificationText(justificationText);
        questionT3.setIncorrectAnswerText(incorrectAnswerText);
        questionT3.setConceptText(conceptText);
    }

    private int generateRandomNumber(long maxNumberOfEntities) {
        final int MIN = 0;
        final int MAX = (int) maxNumberOfEntities;
        return ThreadLocalRandom.current().nextInt(MIN, MAX);
    }
}
