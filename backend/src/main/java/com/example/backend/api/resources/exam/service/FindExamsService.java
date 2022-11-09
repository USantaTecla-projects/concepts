package com.example.backend.api.resources.exam.service;

import com.example.backend.api.resources.exam.ExamRepository;
import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.service.FillerQuestionService;
import com.example.backend.api.resources.exam.exception.specific.ExamNotFoundException;
import com.example.backend.api.resources.exam.model.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class FindExamsService {

    private final ExamRepository examRepository;

    private final FillerQuestionService fillerQuestionService;

    public FindExamsService(
            ExamRepository examRepository,
            FillerQuestionService fillerQuestionService
    ) {
        this.examRepository = examRepository;
        this.fillerQuestionService = fillerQuestionService;
    }

    public Page<Exam> findAllByUserID(final Long userID, final int page) {
        int pageSize = 10;
        return examRepository.findAllByUserID(userID, PageRequest.of(page, pageSize));
    }

    public Exam findByExamID(final Long userID, final Long examID) {
        Exam exam = examRepository
                .findById(examID)
                .orElseThrow(() -> new ExamNotFoundException("The exam with ID " + examID + " wasn't found."));

        if (!Objects.equals(exam.getUserID(), userID)) {
            throw new ExamNotFoundException("The exam with ID " + examID + " doesn't belong to the user with ID " + userID);
        }

        List<Question> questionList = exam.getQuestionList();

        List<Question> filledQuestionList = fillerQuestionService.fillQuestionList(questionList);

        System.out.println(filledQuestionList);
        exam.setQuestionList(filledQuestionList);

        return exam;
    }
}
