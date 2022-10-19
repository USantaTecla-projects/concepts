package com.example.backend.api.resources.exam.service;

import com.example.backend.api.resources.exam.ExamRepository;
import com.example.backend.api.resources.exam.model.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class FindExamsService {

    public final ExamRepository examRepository;

    public FindExamsService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public Page<Exam> findByUserID(final Long userID, final int page) {
        int pageSize = 10;
        return examRepository.findAllByUserID(userID, PageRequest.of(page, pageSize));
    }
}
