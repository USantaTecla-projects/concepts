package com.example.backend.e2e.resources.exam.domain.family.question.tools.generator.specific;

import com.example.backend.e2e.resources.exam.domain.family.question.tools.generator.QuestionGenerator;
import com.example.backend.e2e.resources.exam.domain.family.question.model.Question;
import com.example.backend.e2e.resources.exam.domain.family.question.model.specific.QuestionT2;
import com.example.backend.e2e.resources.exam.domain.family.question.repository.QuestionT2Repository;
import com.example.backend.e2e.resources.exam.domain.factory.Type;
import com.example.backend.e2e.resources.knowledge.concept.ConceptRepository;
import com.example.backend.e2e.resources.knowledge.concept.model.Concept;
import com.example.backend.e2e.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.e2e.resources.knowledge.definition.model.Definition;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class QuestionT2Generator implements QuestionGenerator {

    private final ConceptRepository conceptRepository;
    private final DefinitionRepository definitionRepository;

    private final QuestionT2Repository questionT2Repository;

    public QuestionT2Generator(
            ConceptRepository conceptRepository,
            DefinitionRepository definitionRepository,
            QuestionT2Repository questionT2Repository
    ) {
        this.conceptRepository = conceptRepository;
        this.definitionRepository = definitionRepository;
        this.questionT2Repository = questionT2Repository;
    }


    @Override
    public Question generateQuestion(List<Question> alreadyGeneratedQuestions) {
        final int randomNum = generateRandomNumber();

        List<QuestionT2> questionT2List = findQuestionsT2(alreadyGeneratedQuestions);
        List<Long> usedDefinitionIDs = getUsedDefinitionIDs(questionT2List);

        final Definition definition = getDefinition(randomNum, usedDefinitionIDs);
        if (definition == null) {
            return null;
        }

        final long conceptID = definition.getConceptID();

        final Concept concept = getConcept(conceptID);
        if (concept == null) {
            return null;
        }

        final long definitionID = definition.getId();

        if (questionT2Repository.existsByConceptIDAndDefinitionID(conceptID, definitionID)) {
            QuestionT2 questionT2 = questionT2Repository.findByConceptIDAndDefinitionID(conceptID, definitionID).orElseThrow();
            questionT2.setConceptText(concept.getText());
            questionT2.setDefinitionText(definition.getText());
            questionT2.setFilled(true);
            return questionT2;
        }

        QuestionT2 questionT2 = new QuestionT2();
        extractAndSetQuestionT2Data(questionT2, definition, conceptID, concept);
        return questionT2Repository.save(questionT2);
    }

    private void extractAndSetQuestionT2Data(QuestionT2 questionT2, Definition definition, long conceptID, Concept concept) {
        final String conceptText = concept.getText();
        final String definitionText = definition.getText();
        final long definitionID = definition.getId();

        questionT2.setType(Type.TYPE2);
        questionT2.setConceptText(conceptText);
        questionT2.setConceptID(conceptID);
        questionT2.setDefinitionText(definitionText);
        questionT2.setDefinitionID(definitionID);
        questionT2.setFilled(true);
    }

    private Concept getConcept(long conceptID) {
        return conceptRepository
                .findById(conceptID)
                .orElse(null);
    }

    private Definition getDefinition(int randomNum, List<Long> usedDefinitionIDs) {
        return definitionRepository
                .findRandomDefinition(usedDefinitionIDs, randomNum)
                .orElse(null);
    }

    private static List<Long> getUsedDefinitionIDs(List<QuestionT2> questionT2List) {
        List<Long> usedDefinitionIDs = new LinkedList<>(List.of(-1L));
        if (!questionT2List.isEmpty()) {
            usedDefinitionIDs = questionT2List.stream()
                    .map(QuestionT2::getDefinitionID)
                    .toList();
        }
        return usedDefinitionIDs;
    }

    private List<QuestionT2> findQuestionsT2(List<Question> questions) {
        return questions.stream()
                .filter(question -> question.getType() == Type.TYPE2)
                .map(question -> (QuestionT2) question)
                .toList();
    }

    private int generateRandomNumber() {
        final long numberOfDefinitions = definitionRepository.count();
        final int MIN = 0;
        final int MAX = (int) numberOfDefinitions;
        return ThreadLocalRandom.current().nextInt(MIN, MAX);
    }
}
