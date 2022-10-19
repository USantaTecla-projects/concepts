package com.example.backend.api.resources.exam.domain.family.question.tools.generator.specific;

import com.example.backend.api.resources.exam.domain.family.question.tools.generator.QuestionGenerator;
import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.model.specific.QuestionT2;
import com.example.backend.api.resources.exam.domain.family.question.repository.QuestionT2Repository;
import com.example.backend.api.resources.exam.domain.factory.Type;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.exception.specific.ConceptNotFoundException;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import com.example.backend.api.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.api.resources.knowledge.definition.exception.specific.DefinitionNotFoundException;
import com.example.backend.api.resources.knowledge.definition.model.Definition;
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
        final long conceptID = definition.getConceptID();

        final Concept concept = getConcept(conceptID);
        final long definitionID = definition.getId();

        if (questionT2Repository.existsByConceptIDAndDefinitionID(conceptID, definitionID)) {
            return questionT2Repository.findByConceptIDAndDefinitionID(conceptID, definitionID).orElseThrow();
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
    }

    private Concept getConcept(long conceptID) {
        return conceptRepository
                .findById(conceptID)
                .orElseThrow(() -> {
                    throw new ConceptNotFoundException("The concept with id = " + conceptID + " has not been found");
                });
    }

    private Definition getDefinition(int randomNum, List<Long> usedDefinitionIDs) {
        return definitionRepository
                .findRandomDefinition(usedDefinitionIDs, randomNum).orElseThrow(() -> {
                    throw new DefinitionNotFoundException("No definition was found, probably all definitions have been used in this type of question");
                });
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
