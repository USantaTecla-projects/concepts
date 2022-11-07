package com.example.backend.api.resources.exam.service;

import com.example.backend.api.resources.exam.ExamRepository;
import com.example.backend.api.resources.exam.exception.specific.ExamNotFoundException;
import com.example.backend.api.resources.exam.model.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FindExamsService {

    public final ExamRepository examRepository;

    public FindExamsService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public Page<Exam> findAllByUserID(final Long userID, final int page) {
        int pageSize = 10;
        return examRepository.findAllByUserID(userID, PageRequest.of(page, pageSize));
    }

    public Exam findByExamID(final Long userID, final Long examID) {
        Exam exam = examRepository
                .findById(examID)
                .orElseThrow(() -> new ExamNotFoundException("The exam with ID " + examID + " wasn't found."));

        if(!Objects.equals(exam.getUserID(), userID)){
            throw new ExamNotFoundException("The exam with ID " + examID + " doesn't belong to the user with ID " + userID);
        }

        return exam;
    }
}
