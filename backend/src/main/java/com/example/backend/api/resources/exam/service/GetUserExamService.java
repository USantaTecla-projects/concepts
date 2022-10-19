package com.example.backend.api.resources.exam.service;

import com.example.backend.api.resources.exam.ExamRepository;
import com.example.backend.api.resources.exam.model.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class GetUserExamService {

    public final ExamRepository examRepository;

    public GetUserExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public Page<Exam> getUserExams(final Long userID, final int page) {
        int pageSize = 10;
        return examRepository.findAllByUserID(userID, PageRequest.of(page, pageSize));
    }
}
