package com.example.backend.api.resources.exam.domain.family.question.tools.filler.specific;

import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.model.specific.QuestionT0;
import com.example.backend.api.resources.exam.domain.family.question.tools.filler.QuestionFiller;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.exception.specific.ConceptNotFoundException;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import org.springframework.stereotype.Service;

@Service
public class QuestionT0Filler implements QuestionFiller {

    private final ConceptRepository conceptRepository;

    public QuestionT0Filler(ConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
    }

    @Override
    public Question fillQuestionWithData(Question question) {
        QuestionT0 questionT0 = (QuestionT0) question;
        Long conceptID = questionT0.getConceptID();

        Concept concept = conceptRepository
                .findById(conceptID)
                .orElseThrow(() -> new ConceptNotFoundException("Concept with ID" + conceptID + " not found"));

        questionT0.setConceptText(concept.getText());

        return questionT0;
    }
}
