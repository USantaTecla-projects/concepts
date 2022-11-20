package com.example.backend.e2e.resources.exam;

import com.example.backend.e2e.resources.exam.model.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ExamRepository extends CrudRepository<Exam, Long>, PagingAndSortingRepository<Exam, Long> {

    Optional<Exam> findByIdAndUserID(Long ExamID, Long userID);


    Page<Exam> findAllByUserID(final Long userID, Pageable pageable);

    Page<Exam> findAllByUserIDAndCorrected(final Long userID, Pageable pageable, Boolean isCorrected);

}
