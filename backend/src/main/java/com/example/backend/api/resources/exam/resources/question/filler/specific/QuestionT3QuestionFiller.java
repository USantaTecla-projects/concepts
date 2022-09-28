package com.example.backend.api.resources.exam.resources.question.filler.specific;

import com.example.backend.api.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.api.resources.knowledge.definition.exception.model.DefinitionNotFoundException;
import com.example.backend.api.resources.knowledge.definition.model.Definition;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.exception.model.ConceptNotFoundException;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import com.example.backend.api.resources.knowledge.justification.JustificationRepository;
import com.example.backend.api.resources.knowledge.justification.exception.model.JustificationNotFoundException;
import com.example.backend.api.resources.knowledge.justification.model.Justification;
import com.example.backend.api.resources.exam.resources.question.filler.QuestionFiller;
import com.example.backend.api.resources.exam.resources.question.model.Question;
import com.example.backend.api.resources.exam.resources.question.model.specific.QuestionT3;
import com.example.backend.api.resources.exam.resources.type.Type;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class QuestionT3QuestionFiller implements QuestionFiller {

    private final ConceptRepository conceptRepository;
    private final DefinitionRepository definitionRepository;
    private final JustificationRepository justificationRepository;

    public QuestionT3QuestionFiller(ConceptRepository conceptRepository, DefinitionRepository definitionRepository, JustificationRepository justificationRepository) {
        this.justificationRepository = justificationRepository;
        this.definitionRepository = definitionRepository;
        this.conceptRepository = conceptRepository;
    }

    @Override
    public void fillQuestion(final Question question, List<Question> questions) {
        final int randomNum = generateRandomNumber();

        QuestionT3 questionT3 = (QuestionT3) question;

        List<QuestionT3> questionT3List = findQuestionsT3(questions);
        List<Long> usedJustificationRelatedWithIncorrectDefinitionIDs = new LinkedList<>(List.of(-1L));
        if (!questionT3List.isEmpty()) {
            usedJustificationRelatedWithIncorrectDefinitionIDs = questionT3List.stream()
                    .map(QuestionT3::getJustificationID)
                    .toList();
        }

        final Justification justification = justificationRepository
                .findOneJustificationLinkedToIncorrectDefinition(usedJustificationRelatedWithIncorrectDefinitionIDs, randomNum)
                .orElseThrow(() -> {
                    questionT3.setFilled(false);
                    throw new JustificationNotFoundException("No justification was found, probably all justifications have been used in this type of question");
                });

        final long definitionID = justification.getDefinitionID();
        final long conceptID = justification.getConceptID();
        final long justificationID = justification.getId();

        final Definition incorrectDefinition = definitionRepository
                .findById(definitionID)
                .orElseThrow(() -> {
                    questionT3.setFilled(false);
                    throw new DefinitionNotFoundException("The definition with id = " + definitionID + " has not been found");
                });

        final Concept concept = conceptRepository
                .findById(conceptID)
                .orElseThrow(() -> {
                    questionT3.setFilled(false);
                    throw new ConceptNotFoundException("The concept with id = " + conceptID + " has not been found");
                });

        final String justificationText = justification.getText();
        final String incorrectDefinitionText = incorrectDefinition.getText();
        final String conceptText = concept.getText();

        questionT3.setType(Type.TYPE3);
        questionT3.setJustificationText(justificationText);
        questionT3.setJustificationID(justificationID);
        questionT3.setIncorrectDefinitionText(incorrectDefinitionText);
        questionT3.setDefinitionID(definitionID);
        questionT3.setConceptText(conceptText);
        questionT3.setConceptID(conceptID);
        question.setFilled(true);
    }

    private List<QuestionT3> findQuestionsT3(List<Question> questions) {
        return questions.stream()
                .filter(question -> question.getType() == Type.TYPE3)
                .map(question -> (QuestionT3) question)
                .toList();
    }

    private int generateRandomNumber() {
        final long numberOfIncorrectDefinitions = definitionRepository.countDefinitionByIsCorrect(false);
        final int MIN = 0;
        final int MAX = (int) numberOfIncorrectDefinitions;
        return ThreadLocalRandom.current().nextInt(MIN, MAX);
    }


}
