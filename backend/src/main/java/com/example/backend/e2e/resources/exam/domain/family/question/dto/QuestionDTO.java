package com.example.backend.e2e.resources.exam.domain.family.question.dto;

import com.example.backend.e2e.resources.exam.domain.factory.Type;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
    private Type type;
    Map<String, Object> questionTypeDetails = new LinkedHashMap<>();

    @JsonAnySetter
    void setQuestionTypeDetails(String key, Object value) {
        questionTypeDetails.put(key, value);
    }

    public Optional<Type> getTypeOptional(final Type type) {
        return Optional
                .ofNullable(type);
    }

    public Optional<Map<String, Object>> getQuestionTypeDetailsOptional() {
        return Optional
                .ofNullable(questionTypeDetails);
    }

    @Override
    public String toString() {
        return "QuestionDTO{" +
                "type=" + type +
                ", questionTypeDetails=" + questionTypeDetails +
                '}';
    }
}
