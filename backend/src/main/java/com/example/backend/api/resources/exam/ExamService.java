package com.example.backend.api.resources.exam;

import com.example.backend.api.resources.exam.dto.CreateExamDTO;
import com.example.backend.api.resources.exam.model.Exam;
import com.example.backend.api.resources.exam.question.QuestionService;
import com.example.backend.api.resources.exam.question.exception.model.QuestionDTOBadRequestException;
import com.example.backend.api.resources.exam.question.model.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {

    private final QuestionService questionService;

    public ExamService(QuestionService questionService) {
        this.questionService = questionService;
    }

    public Exam create(CreateExamDTO createExamDTO) {

        final int numberOfQuestions = createExamDTO
                .getNumberOfQuestionsOptional(createExamDTO.getNumberOfQuestions())
                .orElseThrow(() -> new QuestionDTOBadRequestException("Field numberOfQuestions in CreateExam DTO is mandatory"));

        final List<Question> questions = questionService.createQuestions(numberOfQuestions);

        return new Exam(questions);
    }
}
