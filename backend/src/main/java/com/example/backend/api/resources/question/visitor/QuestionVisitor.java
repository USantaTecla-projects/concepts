package com.example.backend.api.resources.question.visitor;

import com.example.backend.api.resources.knowledge.answer.AnswerRepository;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.justification.JustificationRepository;
import com.example.backend.api.resources.question.exception.model.NotEnoughDataException;
import com.example.backend.api.resources.question.models.*;
import com.example.backend.api.resources.question.models.specific.QuestionT0;
import com.example.backend.api.resources.question.models.specific.QuestionT1;
import com.example.backend.api.resources.question.models.specific.QuestionT2;
import com.example.backend.api.resources.question.models.specific.QuestionT3;
import com.example.backend.api.resources.question.visitor.specific.QuestionT0Visitor;
import com.example.backend.api.resources.question.visitor.specific.QuestionT1Visitor;
import com.example.backend.api.resources.question.visitor.specific.QuestionT2Visitor;
import com.example.backend.api.resources.question.visitor.specific.QuestionT3Visitor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class QuestionVisitor implements Visitor {

    public final QuestionT0Visitor questionT0Visitor;
    public final QuestionT1Visitor questionT1Visitor;
    public final QuestionT2Visitor questionT2Visitor;
    public final QuestionT3Visitor questionT3Visitor;

    public final ConceptRepository conceptRepository;
    public final AnswerRepository answerRepository;
    public final JustificationRepository justificationRepository;

    public QuestionVisitor(
            QuestionT0Visitor questionT0Visitor, QuestionT1Visitor questionT1Visitor, QuestionT2Visitor questionT2Visitor, QuestionT3Visitor questionT3Visitor, final ConceptRepository conceptRepository,
            final AnswerRepository answerRepository,
            JustificationRepository justificationRepository) {
        this.questionT0Visitor = questionT0Visitor;
        this.questionT1Visitor = questionT1Visitor;
        this.questionT2Visitor = questionT2Visitor;
        this.questionT3Visitor = questionT3Visitor;
        this.conceptRepository = conceptRepository;
        this.answerRepository = answerRepository;
        this.justificationRepository = justificationRepository;
    }

    @Override
    public void generateQuestionT0(final QuestionT0 questionT0, Map<QuestionType, List<Question>> questionReferences) {
        final long numberOfConcepts = conceptRepository.count();
        if (numberOfConcepts == 0)
            throw new NotEnoughDataException("There are no concepts in database to create the question");

        final int randomNum = generateRandomNumber(numberOfConcepts);
        questionT0Visitor.fillQuestionT0(questionT0, randomNum, questionReferences);
    }

    @Override
    public void generateQuestionT1(final QuestionT1 questionT1, Map<QuestionType, List<Question>> questionReferences) {
        final long numberOfIncorrectAnswers = answerRepository.countAnswerByIsCorrect(false);
        if (numberOfIncorrectAnswers == 0)
            throw new NotEnoughDataException("There are no incorrect answers in the database to create the question");

        final int randomNum = generateRandomNumber(numberOfIncorrectAnswers);
        questionT1Visitor.fillQuestionT1(questionT1, randomNum, questionReferences);
    }

    @Override
    public void generateQuestionT2(final QuestionT2 questionT2, Map<QuestionType, List<Question>> questionReferences) {
        final long numberOfAnswers = answerRepository.count();
        if (numberOfAnswers == 0)
            throw new NotEnoughDataException("There are no answers in database to create the question");

        final int randomNum = generateRandomNumber(numberOfAnswers);
        questionT2Visitor.fillQuestionT2(questionT2, randomNum, questionReferences);


    }

    @Override
    public void generateQuestionT3(final QuestionT3 questionT3, Map<QuestionType, List<Question>> questionReferences) {
        final long numberOfJustifications = justificationRepository.count();
        if (numberOfJustifications == 0)
            throw new NotEnoughDataException("There are no justification in database to create the question");

        final long numberOfIncorrectAnswers = answerRepository.countAnswerByIsCorrect(false);
        if (numberOfIncorrectAnswers == 0)
            throw new NotEnoughDataException("There are no incorrect answers in the database to create the question");

        final int randomNum = generateRandomNumber(numberOfIncorrectAnswers);
        questionT3Visitor.fillQuestionT3(questionT3, randomNum, questionReferences);
    }

    private int generateRandomNumber(long maxNumberOfEntities) {
        final int MIN = 0;
        final int MAX = (int) maxNumberOfEntities;
        return ThreadLocalRandom.current().nextInt(MIN, MAX);
    }
}
