package com.example.backend.api.resources.exam.resources.question.repository;

import com.example.backend.api.resources.exam.resources.question.model.specific.QuestionT0;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionT0Repository extends CrudRepository<QuestionT0, Long> {


    Optional<QuestionT0> findByConceptID(Long conceptID);
}
