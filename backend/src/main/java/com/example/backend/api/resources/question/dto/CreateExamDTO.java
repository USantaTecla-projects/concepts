package com.example.backend.api.resources.question.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateExamDTO {

    private Integer numberOfQuestions;
}
