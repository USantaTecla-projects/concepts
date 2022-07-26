package com.example.backend.api.resources.question;

import com.example.backend.api.resources.question.dto.CreateExamDTO;
import com.example.backend.api.resources.question.models.Question;
import com.example.backend.api.resources.question.models.QuestionT0;
import com.example.backend.api.resources.question.models.QuestionT1;
import com.example.backend.api.resources.question.visitor.Visitor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class QuestionService {

    private final Visitor visitor;

    public QuestionService(Visitor visitor) {
        this.visitor = visitor;
    }

    /**
     * Create an exam by a given DTO (if it is correct).
     *
     * @param createExamDTO The data object to create the exam.
     * @return The created exam.
     * @author Cristian
     */
    public List<Question> create(final CreateExamDTO createExamDTO) {
        // Create empty questions randomly
        List<Question> questions = new LinkedList<>(List.of(new QuestionT0(), new QuestionT1()));

        // Fill those questions with the visitor
        questions.forEach(question -> question.accept(visitor));

        // Return them
        return questions;
    }
}
