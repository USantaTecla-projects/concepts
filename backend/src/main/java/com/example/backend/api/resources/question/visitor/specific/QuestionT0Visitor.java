package com.example.backend.api.resources.question.visitor.specific;

import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import com.example.backend.api.resources.question.models.Question;
import com.example.backend.api.resources.question.models.QuestionType;
import com.example.backend.api.resources.question.models.specific.QuestionT0;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class QuestionT0Visitor {

    public final ConceptRepository conceptRepository;

    public QuestionT0Visitor(ConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
    }

    public void fillQuestionT0(final QuestionT0 questionT0, final int randomNum, Map<QuestionType, List<Question>> questionReferences) {
        List<Question> type0Questions = questionReferences.get(QuestionType.TYPE0);
        List<Long> usedConceptIds = new LinkedList<>(List.of(-1L));
        if (!type0Questions.isEmpty()) {
            usedConceptIds = type0Questions.stream().map(question -> {
                QuestionT0 qT0 = (QuestionT0) question;
                return qT0.getConceptId();
            }).toList();
        }

        final Concept concept = conceptRepository.findRandomConcept(usedConceptIds, randomNum);
        final String conceptText = concept.getText();
        final long conceptId = concept.getId();

        questionT0.setType(QuestionType.TYPE0);
        questionT0.setConceptText(conceptText);
        questionT0.setConceptId(conceptId);
        questionT0.setQuestionAsString(questionT0.questionAsString());
    }
}
