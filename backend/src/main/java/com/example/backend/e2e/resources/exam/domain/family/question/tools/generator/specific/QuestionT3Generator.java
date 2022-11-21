package com.example.backend.e2e.resources.exam.domain.family.question.tools.generator.specific;

import com.example.backend.e2e.resources.exam.domain.factory.Type;
import com.example.backend.e2e.resources.exam.domain.family.question.model.Question;
import com.example.backend.e2e.resources.exam.domain.family.question.model.specific.QuestionT3;
import com.example.backend.e2e.resources.exam.domain.family.question.repository.QuestionT3Repository;
import com.example.backend.e2e.resources.exam.domain.family.question.tools.generator.QuestionGenerator;
import com.example.backend.e2e.resources.knowledge.concept.ConceptRepository;
import com.example.backend.e2e.resources.knowledge.concept.model.Concept;
import com.example.backend.e2e.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.e2e.resources.knowledge.definition.model.Definition;
import com.example.backend.e2e.resources.knowledge.justification.JustificationRepository;
import com.example.backend.e2e.resources.knowledge.justification.model.Justification;
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
        if (justification == null) {
            return null;
        }

        final long justificationID = justification.getId();
        final long definitionID = justification.getDefinitionID();
        final long conceptID = justification.getConceptID();

        final Definition incorrectDefinition = getDefinition(definitionID);
        final Concept concept = getConcept(conceptID);


        if (questionT3Repository.existsByConceptIDAndDefinitionIDAndJustificationID(conceptID, definitionID, justificationID)) {
            QuestionT3 questionT3 = questionT3Repository.findByConceptIDAndDefinitionIDAndJustificationID(conceptID, definitionID, justificationID).orElseThrow();
            questionT3.setConceptText(concept.getText());
            questionT3.setIncorrectDefinitionText(incorrectDefinition.getText());
            questionT3.setJustificationText(justification.getText());
            questionT3.setFilled(true);
            return questionT3;
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
        questionT3.setFilled(true);
    }

    private Concept getConcept(long conceptID) {
        return conceptRepository
                .findById(conceptID)
                .orElse(null);
    }

    private Definition getDefinition(long definitionID) {
        return definitionRepository
                .findById(definitionID)
                .orElse(null);
    }

    private Justification getJustification(int randomNum, List<Long> usedJustificationRelatedWithIncorrectDefinitionIDs) {
        return justificationRepository
                .findOneJustificationLinkedToIncorrectDefinition(usedJustificationRelatedWithIncorrectDefinitionIDs, randomNum)
                .orElse(null);
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
