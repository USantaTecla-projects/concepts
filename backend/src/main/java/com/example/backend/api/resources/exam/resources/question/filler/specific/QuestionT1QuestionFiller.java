package com.example.backend.api.resources.exam.resources.question.filler.specific;

import com.example.backend.api.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.api.resources.knowledge.definition.exception.model.DefinitionNotFoundException;
import com.example.backend.api.resources.knowledge.definition.model.Definition;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.exception.model.ConceptNotFoundException;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import com.example.backend.api.resources.exam.resources.question.filler.QuestionFiller;
import com.example.backend.api.resources.exam.resources.question.model.Question;
import com.example.backend.api.resources.exam.resources.question.model.specific.QuestionT1;
import com.example.backend.api.resources.exam.resources.type.Type;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class QuestionT1QuestionFiller implements QuestionFiller {

    private final ConceptRepository conceptRepository;
    private final DefinitionRepository definitionRepository;

    public QuestionT1QuestionFiller(ConceptRepository conceptRepository, DefinitionRepository definitionRepository) {
        this.conceptRepository = conceptRepository;
        this.definitionRepository = definitionRepository;
    }

    @Override
    public void fillQuestion(final Question question, List<Question> questions) {
        final int randomNum = generateRandomNumber();
        QuestionT1 questionT1 = (QuestionT1) question;

        List<QuestionT1> questionT1List = findQuestionsT1(questions);
        List<Long> usedIncorrectDefinitionIDs = new LinkedList<>(List.of(-1L));
        if (!questionT1List.isEmpty()) {
            usedIncorrectDefinitionIDs = questionT1List.stream()
                    .map(QuestionT1::getDefinitionID)
                    .toList();
        }

        final Definition definition = definitionRepository
                .findRandomDefinitionBasedOnCorrect(usedIncorrectDefinitionIDs, false, randomNum)
                .orElseThrow(() -> {
                    questionT1.setFilled(false);
                    throw new DefinitionNotFoundException("No definition was found, probably all definitions have been used in this type of question");
                });

        final long conceptId = definition.getConceptId();
        final Concept concept = conceptRepository
                .findById(conceptId)
                .orElseThrow(() ->{
                    questionT1.setFilled(false);
                    throw new ConceptNotFoundException("The concept with id = " + conceptId + " has not been found");
                });

        final String conceptText = concept.getText();
        final String incorrectDefinitionText = definition.getText();
        final long definitionID = definition.getId();

        questionT1.setType(Type.TYPE1);
        questionT1.setConceptText(conceptText);
        questionT1.setConceptID(conceptId);
        questionT1.setIncorrectDefinitionText(incorrectDefinitionText);
        questionT1.setDefinitionID(definitionID);
        questionT1.setFilled(true);
    }

    private List<QuestionT1> findQuestionsT1(List<Question> questions) {
        return questions.stream()
                .filter(question -> question.getType() == Type.TYPE1)
                .map(question -> (QuestionT1) question)
                .toList();
    }

    private int generateRandomNumber() {
        final long numberOfIncorrectDefinitions = definitionRepository.countDefinitionByIsCorrect(false);
        final int MIN = 0;
        final int MAX = (int) numberOfIncorrectDefinitions;
        return ThreadLocalRandom.current().nextInt(MIN, MAX);
    }
}
