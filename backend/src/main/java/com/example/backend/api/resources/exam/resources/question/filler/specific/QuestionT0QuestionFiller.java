package com.example.backend.api.resources.exam.resources.question.filler.specific;

import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.exception.model.ConceptNotFoundException;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import com.example.backend.api.resources.exam.resources.question.model.Question;
import com.example.backend.api.resources.exam.resources.type.Type;
import com.example.backend.api.resources.exam.resources.question.model.specific.QuestionT0;
import com.example.backend.api.resources.exam.resources.question.filler.QuestionFiller;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class QuestionT0QuestionFiller implements QuestionFiller {

    private final ConceptRepository conceptRepository;

    public QuestionT0QuestionFiller(ConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
    }

    @Override
    public void fillQuestion(final Question question, List<Question> questions) {
        final int randomNum = generateRandomNumber();

        QuestionT0 questionT0 = (QuestionT0) question;
        List<QuestionT0> questionT0List = findQuestionsT0(questions);
        List<Long> usedConceptIds = new LinkedList<>(List.of(-1L));
        if (!questionT0List.isEmpty()) {
            usedConceptIds = questionT0List.stream()
                    .map(QuestionT0::getConceptID)
                    .toList();
        }

        final Concept concept = conceptRepository
                .findRandomConcept(usedConceptIds, randomNum)
                .orElseThrow(() -> {
                    questionT0.setFilled(false);
                    throw new ConceptNotFoundException("No concept was found, probably all concepts have been used in this type of question");
                });

        final String conceptText = concept.getText();
        final long conceptId = concept.getId();

        questionT0.setType(Type.TYPE0);
        questionT0.setConceptText(conceptText);
        questionT0.setConceptID(conceptId);
        questionT0.setFilled(true);
    }

    private List<QuestionT0> findQuestionsT0(List<Question> questions) {
        return questions.stream()
                .filter(question -> question.getType() == Type.TYPE0)
                .map(question -> (QuestionT0) question)
                .toList();
    }

    private int generateRandomNumber() {
        final long numberOfConcepts = conceptRepository.count();
        final int MIN = 0;
        final int MAX = (int) numberOfConcepts;
        return ThreadLocalRandom.current().nextInt(MIN, MAX);
    }
}
