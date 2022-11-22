package com.example.backend.api.resources.exam.domain.family.question.tools.filler.specific;

import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.model.specific.QuestionT3;
import com.example.backend.api.resources.exam.domain.family.question.tools.filler.QuestionFiller;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.exception.specific.ConceptNotFoundException;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import com.example.backend.api.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.api.resources.knowledge.definition.exception.specific.DefinitionNotFoundException;
import com.example.backend.api.resources.knowledge.definition.model.Definition;
import com.example.backend.api.resources.knowledge.justification.JustificationRepository;
import com.example.backend.api.resources.knowledge.justification.exception.specific.JustificationNotFoundException;
import com.example.backend.api.resources.knowledge.justification.model.Justification;

public class QuestionT3Filler implements QuestionFiller {

    private final ConceptRepository conceptRepository;
    private final DefinitionRepository definitionRepository;
    private final JustificationRepository justificationRepository;

    public QuestionT3Filler(
            ConceptRepository conceptRepository,
            DefinitionRepository definitionRepository,
            JustificationRepository justificationRepository) {
        this.conceptRepository = conceptRepository;
        this.definitionRepository = definitionRepository;
        this.justificationRepository = justificationRepository;
    }

    @Override
    public Question fillQuestionWithData(Question question) {
        QuestionT3 question3 = (QuestionT3) question;
        Long conceptID = question3.getConceptID();
        Long definitionID = question3.getDefinitionID();
        Long justificationID = question3.getJustificationID();

        Concept concept = conceptRepository
                .findById(conceptID)
                .orElseThrow(() -> new ConceptNotFoundException("Concept with ID" + conceptID + " not found"));

        Definition incorrectDefinition = definitionRepository
                .findById(definitionID)
                .orElseThrow(() -> new DefinitionNotFoundException("Definition with ID" + definitionID + " not found"));

        Justification justification = justificationRepository
                .findById(justificationID)
                .orElseThrow(() -> new JustificationNotFoundException("Justification with ID" + justificationID + " not found"));

        question3.setConceptText(concept.getText());
        question3.setIncorrectDefinitionText(incorrectDefinition.getText());
        question3.setJustificationText(justification.getText());

        return  question3;
    }
}
