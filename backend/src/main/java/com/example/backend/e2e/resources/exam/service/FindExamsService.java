package com.example.backend.e2e.resources.exam.service;

import com.example.backend.e2e.resources.exam.ExamRepository;
import com.example.backend.e2e.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.e2e.resources.exam.domain.family.question.model.Question;
import com.example.backend.e2e.resources.exam.domain.family.question.service.FillQuestionService;
import com.example.backend.e2e.resources.exam.exception.specific.ExamNotFoundException;
import com.example.backend.e2e.resources.exam.model.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class FindExamsService {

    private final ExamRepository examRepository;

    private final FillQuestionService fillQuestionService;

    public FindExamsService(
            ExamRepository examRepository,
            FillQuestionService fillQuestionService
    ) {
        this.examRepository = examRepository;
        this.fillQuestionService = fillQuestionService;
    }

    public Page<Exam> findAllByUserID(final Long userID, final int page, final Boolean isCorrected) {
        int pageSize = 10;

        return examRepository.findAllByUserIDAndCorrected(
                userID,
                PageRequest.of(page, pageSize),
                isCorrected
        );
    }

    public Exam findByExamID(final Long userID, final Long examID) {
        Exam exam = examRepository
                .findById(examID)
                .orElseThrow(() -> new ExamNotFoundException("The exam with ID " + examID + " wasn't found."));

        if (!Objects.equals(exam.getUserID(), userID)) {
            throw new ExamNotFoundException("The exam with ID " + examID + " doesn't belong to the user with ID " + userID);
        }

        List<Question> questionList = exam.getQuestionList();
        List<Question> filledQuestionList = fillQuestionService.fillQuestionList(questionList);
        exam.setQuestionList(filledQuestionList);

        List<Answer> answerList = exam.getAnswerList();
        exam.setAnswerList(answerList);

        return exam;
    }
}
