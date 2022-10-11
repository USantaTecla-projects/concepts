package com.example.backend.api.resources.exam.module.question.filler.specific;

import com.example.backend.api.resources.exam.module.question.repository.QuestionT3Repository;
import com.example.backend.api.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.api.resources.knowledge.definition.exception.specific.DefinitionNotFoundException;
import com.example.backend.api.resources.knowledge.definition.model.Definition;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.exception.specific.ConceptNotFoundException;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import com.example.backend.api.resources.knowledge.justification.JustificationRepository;
import com.example.backend.api.resources.knowledge.justification.exception.specific.JustificationNotFoundException;
import com.example.backend.api.resources.knowledge.justification.model.Justification;
import com.example.backend.api.resources.exam.module.question.filler.QuestionGenerator;
import com.example.backend.api.resources.exam.module.question.model.Question;
import com.example.backend.api.resources.exam.module.question.model.specific.QuestionT3;
import com.example.backend.api.resources.exam.module.type.Type;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class QuestionT3Generator implements QuestionGenerator {

    private final ConceptRepository conceptRepository;
    private final DefinitionRepository definitionRepository;
    private final JustificationRepository justificationRepository;
    private final QuestionT3Repository questionT3Repository;

    public QuestionT3Generator(
            ConceptRepository conceptRepository,
            DefinitionRepository definitionRepository,
            JustificationRepository justificationRepository,
            QuestionT3Repository questionT3Repository
    ) {
        this.justificationRepository = justificationRepository;
        this.definitionRepository = definitionRepository;
        this.conceptRepository = conceptRepository;
        this.questionT3Repository = questionT3Repository;
    }

    @Override
    public Question generateQuestion(List<Question> alreadyGeneratedQuestions) {
        final int randomNum = generateRandomNumber();

        List<QuestionT3> questionT3List = findQuestionsT3(alreadyGeneratedQuestions);
        List<Long> usedJustificationRelatedWithIncorrectDefinitionIDs = getUsedJustificationRelatedWithIncorrectDefinitionIDs(questionT3List);

        final Justification justification = getJustification(randomNum, usedJustificationRelatedWithIncorrectDefinitionIDs);
        final long definitionID = justification.getDefinitionID();


        final Definition incorrectDefinition = getDefinition(definitionID);
        final long conceptID = justification.getConceptID();

        final Concept concept = getConcept(conceptID);
        final long justificationID = justification.getId();

        if (questionT3Repository.existsByConceptIDAndDefinitionIDAndJustificationID(conceptID, definitionID, justificationID)) {
            System.out.println("Exists T3");
            return questionT3Repository.findByConceptIDAndDefinitionIDAndJustificationID(conceptID, definitionID, justificationID).orElseThrow();
        }

        QuestionT3 questionT3 = new QuestionT3();
        extractAndSetQuestionT3Data(questionT3, justification, definitionID, conceptID, justificationID, incorrectDefinition, concept);
        return questionT3Repository.save(questionT3);
    }

    private void extractAndSetQuestionT3Data(QuestionT3 questionT3, Justification justification, long definitionID, long conceptID, long justificationID, Definition incorrectDefinition, Concept concept) {
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
    }

    private Concept getConcept(long conceptID) {
        return conceptRepository
                .findById(conceptID)
                .orElseThrow(() -> {
                    throw new ConceptNotFoundException("The concept with id = " + conceptID + " has not been found");
                });
    }

    private Definition getDefinition(long definitionID) {
        return definitionRepository
                .findById(definitionID)
                .orElseThrow(() -> {
                    throw new DefinitionNotFoundException("The definition with id = " + definitionID + " has not been found");
                });
    }

    private Justification getJustification(int randomNum, List<Long> usedJustificationRelatedWithIncorrectDefinitionIDs) {
        return justificationRepository
                .findOneJustificationLinkedToIncorrectDefinition(usedJustificationRelatedWithIncorrectDefinitionIDs, randomNum)
                .orElseThrow(() -> {
                    throw new JustificationNotFoundException("No justification was found, probably all justifications have been used in this type of question");
                });
    }

    private List<Long> getUsedJustificationRelatedWithIncorrectDefinitionIDs(List<QuestionT3> questionT3List) {
        List<Long> usedJustificationRelatedWithIncorrectDefinitionIDs = new LinkedList<>(List.of(-1L));
        if (!questionT3List.isEmpty()) {
            usedJustificationRelatedWithIncorrectDefinitionIDs = questionT3List.stream()
                    .map(QuestionT3::getJustificationID)
                    .toList();
        }
        return usedJustificationRelatedWithIncorrectDefinitionIDs;
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
