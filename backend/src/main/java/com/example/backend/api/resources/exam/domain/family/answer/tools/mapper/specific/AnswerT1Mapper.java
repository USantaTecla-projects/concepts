package com.example.backend.api.resources.exam.domain.family.answer.tools.mapper.specific;

import com.example.backend.api.resources.exam.domain.factory.Type;
import com.example.backend.api.resources.exam.domain.family.answer.exception.specific.AnswerNotFoundException;
import com.example.backend.api.resources.exam.domain.family.answer.tools.mapper.AnswerMapper;
import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.api.resources.exam.domain.family.answer.model.specific.AnswerT1;
import com.example.backend.api.resources.exam.domain.family.answer.repository.AnswerT1Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AnswerT1Mapper extends AnswerMapper {

    private final AnswerT1Repository answerT1Repository;

    public AnswerT1Mapper(AnswerT1Repository answerT1Repository) {
        this.answerT1Repository = answerT1Repository;
    }

    @Override
    public Answer getAnswerFromDTO() {
        String reply = mapJsonReply(replyAsJson);

        boolean answerAlreadyExist = answerT1Repository.existsById(id);
        if(!answerAlreadyExist){
            AnswerT1 answerT1 = new AnswerT1();
            updateAnswerInfo(reply, answerT1);

            return answerT1;
        }

        AnswerT1 answerT1 = answerT1Repository
                .findById(id)
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found"));

        updateAnswerInfo(reply, answerT1);

        return answerT1;
    }

    private void updateAnswerInfo(String reply, AnswerT1 answerT1) {
        answerT1.setType(Type.TYPE1);
        answerT1.setUserID(userID);
        answerT1.setQuestionID(questionID);
        answerT1.setReply(reply);
        answerT1.setCorrectionStatus(correctionStatus);
    }

    private String mapJsonReply(JsonNode replyAsJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.treeToValue(replyAsJson, String.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
