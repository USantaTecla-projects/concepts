package com.example.backend.api.resources.exam.domain.family.answer.mapper.specific;

import com.example.backend.api.resources.exam.domain.family.answer.mapper.AnswerMapper;
import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.api.resources.exam.domain.family.answer.model.specific.AnswerT0;
import com.example.backend.api.resources.exam.domain.factory.Type;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AnswerT0Mapper extends AnswerMapper {

    @Override
    public Answer getAnswerFromDTO() {
        String reply = mapJsonReply(replyAsJson);

        AnswerT0 answerT0 = new AnswerT0();
        answerT0.setType(Type.TYPE0);
        answerT0.setUserID(userID);
        answerT0.setReply(reply);

        return answerT0;
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
