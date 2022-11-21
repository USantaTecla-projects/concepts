package com.example.backend.e2e.resources.exam.domain.family.question.tools.generator.specific;

import com.example.backend.e2e.resources.exam.domain.family.question.model.Question;
import com.example.backend.e2e.resources.exam.domain.family.question.model.specific.QuestionT1;
import com.example.backend.e2e.resources.exam.domain.family.question.repository.QuestionT1Repository;
import com.example.backend.e2e.resources.exam.domain.family.question.tools.generator.QuestionGenerator;
import com.example.backend.e2e.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.e2e.resources.knowledge.definition.model.Definition;
import com.example.backend.e2e.resources.knowledge.concept.ConceptRepository;
import com.example.backend.e2e.resources.knowledge.concept.model.Concept;
import com.example.backend.e2e.resources.exam.domain.factory.Type;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class QuestionT1Generator implements QuestionGenerator {

    private final ConceptRepository conceptRepository;
    private final DefinitionRepository definitionRepository;

    private final QuestionT1Repository questionT1Repository;

    public QuestionT1Generator(
            ConceptRepository conceptRepository,
            DefinitionRepository definitionRepository,
            QuestionT1Repository questionT1Repository
    ) {
        this.conceptRepository = conceptRepository;
        this.definitionRepository = definitionRepository;
        this.questionT1Repository = questionT1Repository;
    }

    @Override
    public Question generateQuestion(List<Question> alreadyGeneratedQuestions) {
        final int randomNum = generateRandomNumber();

        List<QuestionT1> questionT1List = findQuestionsT1(alreadyGeneratedQuestions);
        List<Long> usedIncorrectDefinitionIDs = getUsedIncorrectDefinitionIDs(questionT1List);

        final Definition definition = getDefinition(randomNum, usedIncorrectDefinitionIDs);
        if (definition == null) {
            return null;
        }

        final long conceptID = definition.getConceptID();
        final long definitionID = definition.getId();

        final Concept concept = getConcept(conceptID);

        if (questionT1Repository.existsByConceptIDAndDefinitionID(conceptID, definitionID)) {
            QuestionT1 questionT1 = questionT1Repository.findByConceptIDAndDefinitionID(conceptID, definitionID).orElseThrow();
            questionT1.setConceptText(concept.getText());
            questionT1.setIncorrectDefinitionText(definition.getText());
            questionT1.setFilled(true);
            return questionT1;
        }

        QuestionT1 questionT1 = new QuestionT1();
        extractAndSetQuestionT1Data(questionT1, definition, concept, conceptID);
        return questionT1Repository.save(questionT1);
    }

    private void extractAndSetQuestionT1Data(QuestionT1 questionT1, Definition definition, Concept concept, long conceptID) {
        final String conceptText = concept.getText();
        final String incorrectDefinitionText = definition.getText();
        final long definitionID = definition.getId();

        questionT1.setType(Type.TYPE1);
        questionT1.setConceptText(conceptText);
        questionT1.setConceptID(conceptID);
        questionT1.setIncorrectDefinitionText(incorrectDefinitionText);
        questionT1.setDefinitionID(definitionID);
        questionT1.setFilled(true);
    }

    private Concept getConcept(long conceptID) {
        return conceptRepository
                .findById(conceptID)
                .orElse(null);
    }

    private Definition getDefinition(int randomNum, List<Long> usedIncorrectDefinitionIDs) {
        return definitionRepository
                .findRandomDefinitionBasedOnCorrect(usedIncorrectDefinitionIDs, false, randomNum)
                .orElse(null);
    }

    private List<Long> getUsedIncorrectDefinitionIDs(List<QuestionT1> questionT1List) {
        List<Long> usedIncorrectDefinitionIDs = new LinkedList<>(List.of(-1L));
        if (!questionT1List.isEmpty()) {
            usedIncorrectDefinitionIDs = questionT1List.stream()
                    .map(QuestionT1::getDefinitionID)
                    .toList();
        }
        return usedIncorrectDefinitionIDs;
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
