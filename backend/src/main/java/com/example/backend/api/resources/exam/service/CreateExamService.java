package com.example.backend.api.resources.exam.service;

import com.example.backend.api.resources.exam.dto.CreateExamDTO;
import com.example.backend.api.resources.exam.exception.specific.CreateExamDTOBadRequestException;
import com.example.backend.api.resources.exam.exception.specific.UpdateExamDTOBadRequestException;
import com.example.backend.api.resources.exam.model.Exam;
import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.service.CreateQuestionService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CreateExamService {

    private final CreateQuestionService createQuestionService;

    public CreateExamService(CreateQuestionService createQuestionService) {
        this.createQuestionService = createQuestionService;
    }

    public Exam create(final CreateExamDTO createExamDTO) {

        final Long userID = createExamDTO
                .getUserIDOptional(createExamDTO.getUserID())
                .orElseThrow(() -> new UpdateExamDTOBadRequestException("Field userID in CreateExam DTO is mandatory"));

        final int numberOfQuestions = createExamDTO
                .getNumberOfQuestionsOptional(createExamDTO.getNumberOfQuestions())
                .orElseThrow(() -> new CreateExamDTOBadRequestException("Field numberOfQuestions in CreateExam DTO is mandatory"));

        final List<Question> questions = createQuestionService.createQuestionList(numberOfQuestions);

        return new Exam(questions, userID, new Timestamp(System.currentTimeMillis()));
    }
}
