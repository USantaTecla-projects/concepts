package com.example.backend.api.resources.exam.question.filler.specific;

import com.example.backend.api.resources.knowledge.answer.AnswerRepository;
import com.example.backend.api.resources.knowledge.answer.exception.model.AnswerNotFoundException;
import com.example.backend.api.resources.knowledge.answer.model.Answer;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.exception.model.ConceptNotFoundException;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import com.example.backend.api.resources.exam.question.filler.Filler;
import com.example.backend.api.resources.exam.question.model.Question;
import com.example.backend.api.resources.exam.question.model.specific.QuestionT1;
import com.example.backend.api.resources.exam.type.QuestionType;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class QuestionT1Filler implements Filler {

    private final ConceptRepository conceptRepository;
    private final AnswerRepository answerRepository;

    public QuestionT1Filler(ConceptRepository conceptRepository, AnswerRepository answerRepository) {
        this.conceptRepository = conceptRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public void fillQuestion(final Question question, List<Question> questions) {
        final int randomNum = generateRandomNumber();
        QuestionT1 questionT1 = (QuestionT1) question;

        List<QuestionT1> questionT1List = findQuestionsT1(questions);
        List<Long> usedIncorrectAnswerIds = new LinkedList<>(List.of(-1L));
        if (!questionT1List.isEmpty()) {
            usedIncorrectAnswerIds = questionT1List.stream()
                    .map(QuestionT1::getAnswerId)
                    .toList();
        }

        final Answer answer = answerRepository
                .findRandomAnswerBasedOnCorrect(usedIncorrectAnswerIds, false, randomNum)
                .orElseThrow(() -> {
                    questionT1.setFilled(false);
                    throw new AnswerNotFoundException("No answer was found, probably all answers have been used in this type of question");
                });

        final long conceptId = answer.getConceptId();
        final Concept concept = conceptRepository
                .findById(conceptId)
                .orElseThrow(() ->{
                    questionT1.setFilled(false);
                    throw new ConceptNotFoundException("The concept with id = " + conceptId + " has not been found");
                });

        final String conceptText = concept.getText();
        final String incorrectAnswerText = answer.getText();
        final long answerId = answer.getId();

        questionT1.setType(QuestionType.TYPE1);
        questionT1.setConceptText(conceptText);
        questionT1.setConceptId(conceptId);
        questionT1.setIncorrectAnswerText(incorrectAnswerText);
        questionT1.setAnswerId(answerId);
        questionT1.setQuestionAsString(questionT1.questionAsString());
        questionT1.setFilled(true);
    }

    private List<QuestionT1> findQuestionsT1(List<Question> questions) {
        return questions.stream()
                .filter(question -> question.getType() == QuestionType.TYPE1)
                .map(question -> (QuestionT1) question)
                .toList();
    }

    private int generateRandomNumber() {
        final long numberOfIncorrectAnswers = answerRepository.countAnswerByIsCorrect(false);
        final int MIN = 0;
        final int MAX = (int) numberOfIncorrectAnswers;
        return ThreadLocalRandom.current().nextInt(MIN, MAX);
    }
}
