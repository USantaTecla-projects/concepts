package com.example.backend.api.resources.exam.resources.question.filler.specific;

import com.example.backend.api.resources.exam.resources.question.filler.QuestionFiller;
import com.example.backend.api.resources.exam.resources.question.model.Question;
import com.example.backend.api.resources.exam.resources.question.model.specific.QuestionT0;
import com.example.backend.api.resources.exam.resources.question.repository.QuestionT0Repository;
import com.example.backend.api.resources.exam.resources.type.Type;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.exception.model.ConceptNotFoundException;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class QuestionT0QuestionFiller implements QuestionFiller {

    private final ConceptRepository conceptRepository;

    private final QuestionT0Repository questionT0Repository;

    public QuestionT0QuestionFiller(ConceptRepository conceptRepository,QuestionT0Repository questionT0Repository) {
        this.conceptRepository = conceptRepository;
        this.questionT0Repository = questionT0Repository;
    }

    @Override
    public void fillQuestion(final Question question, List<Question> questions) {
        final int randomNum = generateRandomNumber();

        QuestionT0 questionT0 = (QuestionT0) question;
        List<QuestionT0> questionT0List = findQuestionsT0(questions);
        List<Long> usedConceptIds = getUsedConceptIDs(questionT0List);

        final Concept concept = getConcept(randomNum, questionT0, usedConceptIds);

        extractAndSetQuestionT0Data(questionT0, concept);

        final Long conceptID = concept.getId();

        if(questionT0Repository.findByConceptID(conceptID).isEmpty()){
            questionT0Repository.save(questionT0);
        }
    }

    private void extractAndSetQuestionT0Data(QuestionT0 questionT0, Concept concept) {
        final String conceptText = concept.getText();
        final long conceptID = concept.getId();

        questionT0.setType(Type.TYPE0);
        questionT0.setConceptText(conceptText);
        questionT0.setConceptID(conceptID);
        questionT0.setFilled(true);
    }

    private Concept getConcept(int randomNum, QuestionT0 questionT0, List<Long> usedConceptIds) {
        return conceptRepository
                .findRandomConcept(usedConceptIds, randomNum)
                .orElseThrow(() -> {
                    questionT0.setFilled(false);
                    throw new ConceptNotFoundException("No concept was found, probably all concepts have been used in this type of question");
                });
    }

    private List<Long> getUsedConceptIDs(List<QuestionT0> questionT0List) {
        List<Long> usedConceptIds = new LinkedList<>(List.of(-1L));
        if (!questionT0List.isEmpty()) {
            usedConceptIds = questionT0List.stream()
                    .map(QuestionT0::getConceptID)
                    .toList();
        }
        return usedConceptIds;
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
