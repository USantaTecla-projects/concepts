package com.example.backend.api.resources.exam.dto;

import com.example.backend.api.resources.exam.resources.type.Type;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.JsonNode;
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
    private JsonNode answer;

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

    public Optional<JsonNode> getAnswerOptional() {
        return Optional
                .ofNullable(answer);
    }

    @Override
    public String toString() {
        return "QuestionDTO{" +
                "type=" + type +
                ", questionTypeDetails=" + questionTypeDetails +
                ", answer=" + answer +
                '}';
    }
}
