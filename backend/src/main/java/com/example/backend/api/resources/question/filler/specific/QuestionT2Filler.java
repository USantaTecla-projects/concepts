package com.example.backend.api.resources.question.filler.specific;

import com.example.backend.api.resources.knowledge.answer.AnswerRepository;
import com.example.backend.api.resources.knowledge.answer.exception.model.AnswerNotFoundException;
import com.example.backend.api.resources.knowledge.answer.model.Answer;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.exception.model.ConceptNotFoundException;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import com.example.backend.api.resources.question.filler.Filler;
import com.example.backend.api.resources.question.models.Question;
import com.example.backend.api.resources.question.models.specific.QuestionT2;
import com.example.backend.api.resources.question.type.QuestionType;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class QuestionT2Filler implements Filler {

    private final ConceptRepository conceptRepository;
    private final AnswerRepository answerRepository;

    public QuestionT2Filler(ConceptRepository conceptRepository, AnswerRepository answerRepository) {
        this.conceptRepository = conceptRepository;
        this.answerRepository = answerRepository;
    }


    @Override
    public void fillQuestion(final Question question, List<Question> questions) {
        final int randomNum = generateRandomNumber();

        QuestionT2 questionT2 = (QuestionT2) question;

        List<QuestionT2> questionT2List = findQuestionsT2(questions);
        List<Long> usedAnswerIds = new LinkedList<>(List.of(-1L));
        if (!questionT2List.isEmpty()) {
            usedAnswerIds = questionT2List.stream()
                    .map(QuestionT2::getAnswerId)
                    .toList();
        }

        final Answer answer = answerRepository
                .findRandomAnswer(usedAnswerIds, randomNum).orElseThrow(() -> {
                    questionT2.setFilled(false);
                    throw new AnswerNotFoundException("No answer was found, probably all answers have been used in this type of question");
                });


        final long conceptId = answer.getConceptId();
        final Concept concept = conceptRepository
                .findById(conceptId)
                .orElseThrow(() -> {
                    questionT2.setFilled(false);
                    throw new ConceptNotFoundException("The concept with id = " + conceptId + " has not been found");
                });

        final String conceptText = concept.getText();
        final String answerText = answer.getText();
        final long answerId = answer.getId();

        questionT2.setType(QuestionType.TYPE2);
        questionT2.setConceptText(conceptText);
        questionT2.setConceptId(conceptId);
        questionT2.setAnswerText(answerText);
        questionT2.setAnswerId(answerId);
        questionT2.setQuestionAsString(questionT2.questionAsString());
        question.setFilled(true);
    }

    private List<QuestionT2> findQuestionsT2(List<Question> questions) {
        return questions.stream()
                .filter(question -> question.getType() == QuestionType.TYPE2)
                .map(question -> (QuestionT2) question)
                .toList();
    }

    private int generateRandomNumber() {
        final long numberOfAnswers = answerRepository.count();
        final int MIN = 0;
        final int MAX = (int) numberOfAnswers;
        return ThreadLocalRandom.current().nextInt(MIN, MAX);
    }
}
