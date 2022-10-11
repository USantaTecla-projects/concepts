package com.example.backend.api.resources.exam;

import com.example.backend.api.resources.exam.model.Exam;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ExamRepository extends CrudRepository<Exam, Long> {

    Optional<Exam> findTopByUserIDOrderByCreationDate(Long userID);

}
