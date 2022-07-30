package com.example.backend.api.resources.question.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateExamDTO {

    private Integer numberOfQuestions;

    public Optional<Integer> getNumberOfQuestionsOptional(final Integer numberOfQuestions) {
        return Optional
                .ofNullable(numberOfQuestions)
                .map((n) -> n > 0 ? n : null);
    }
}
