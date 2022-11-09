package com.example.backend.api.resources.exam.domain.family.question.tools.filler.specific;

import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.model.specific.QuestionT2;
import com.example.backend.api.resources.exam.domain.family.question.tools.filler.QuestionFiller;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.exception.specific.ConceptNotFoundException;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import com.example.backend.api.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.api.resources.knowledge.definition.exception.specific.DefinitionNotFoundException;
import com.example.backend.api.resources.knowledge.definition.model.Definition;
import org.springframework.stereotype.Service;

@Service
public class QuestionT2Filler implements QuestionFiller {

    private final ConceptRepository conceptRepository;
    private final DefinitionRepository definitionRepository;

    public QuestionT2Filler(
            ConceptRepository conceptRepository,
            DefinitionRepository definitionRepository
    ) {
        this.conceptRepository = conceptRepository;
        this.definitionRepository = definitionRepository;
    }

    @Override
    public Question fillQuestionWithData(Question question) {
        QuestionT2 question2 = (QuestionT2) question;
        Long conceptID = question2.getConceptID();
        Long definitionID = question2.getDefinitionID();

        Concept concept = conceptRepository
                .findById(conceptID)
                .orElseThrow(() -> new ConceptNotFoundException("Concept with ID" + conceptID + " not found"));

        Definition definition = definitionRepository
                .findById(definitionID)
                .orElseThrow(() -> new DefinitionNotFoundException("Definition with ID" + definitionID + " not found"));

        question2.setConceptText(concept.getText());
        question2.setDefinitionText(definition.getText());

        return question2;
    }
}
