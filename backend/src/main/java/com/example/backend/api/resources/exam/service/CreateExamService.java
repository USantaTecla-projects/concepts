package com.example.backend.api.resources.exam.service;

import com.example.backend.api.resources.exam.ExamRepository;
import com.example.backend.api.resources.exam.dto.CreateExamDTO;
import com.example.backend.api.resources.exam.exception.specific.CreateExamDTOBadRequestException;
import com.example.backend.api.resources.exam.exception.specific.ReplyExamDTOBadRequestException;
import com.example.backend.api.resources.exam.model.Exam;
import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.service.CreateQuestionService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CreateExamService {

    private final ExamRepository examRepository;
    private final CreateQuestionService createQuestionService;


    public CreateExamService(
            ExamRepository examRepository,
            CreateQuestionService createQuestionService
    ) {
        this.examRepository = examRepository;
        this.createQuestionService = createQuestionService;
    }

    public Exam create(final CreateExamDTO createExamDTO) {

        final Long userID = createExamDTO
                .getUserIDOptional(createExamDTO.getUserID())
                .orElseThrow(() -> new ReplyExamDTOBadRequestException("Field userID in CreateExam DTO is mandatory"));

        final int numberOfQuestions = createExamDTO
                .getNumberOfQuestionsOptional(createExamDTO.getNumberOfQuestions())
                .orElseThrow(() -> new CreateExamDTOBadRequestException("Field numberOfQuestions in CreateExam DTO is mandatory"));

        final List<Question> questions = createQuestionService.createQuestionList(numberOfQuestions);

        return createExamOnDatabase(userID, questions);
    }

    private Exam createExamOnDatabase(final Long userID, final List<Question> questionList) {
        final Exam exam = new Exam(questionList, userID, new Timestamp(System.currentTimeMillis()));
        return examRepository.save(exam);
    }
}
