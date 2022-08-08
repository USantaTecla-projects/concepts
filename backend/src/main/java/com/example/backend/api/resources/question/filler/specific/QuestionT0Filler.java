package com.example.backend.api.resources.question.filler.specific;

import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.exception.model.ConceptNotFoundException;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import com.example.backend.api.resources.question.models.Question;
import com.example.backend.api.resources.question.type.QuestionType;
import com.example.backend.api.resources.question.type.TypeData;
import com.example.backend.api.resources.question.models.specific.QuestionT0;
import com.example.backend.api.resources.question.filler.Filler;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class QuestionT0Filler implements Filler {

    private final ConceptRepository conceptRepository;

    public QuestionT0Filler(ConceptRepository conceptRepository) {
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
                    .map(QuestionT0::getConceptId)
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

        questionT0.setType(QuestionType.TYPE0);
        questionT0.setConceptText(conceptText);
        questionT0.setConceptId(conceptId);
        questionT0.setQuestionAsString(questionT0.questionAsString());
        questionT0.setFilled(true);
    }

    private List<QuestionT0> findQuestionsT0(List<Question> questions) {
        return questions.stream()
                .filter(question -> question.getType() == QuestionType.TYPE0)
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
