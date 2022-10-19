package com.example.backend.api.resources.exam.domain.family.answer.mapper.specific;

import com.example.backend.api.resources.exam.domain.family.answer.mapper.AnswerMapper;
import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.api.resources.exam.domain.family.answer.model.specific.AnswerT2;
import com.example.backend.api.resources.exam.domain.factory.Type;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AnswerT2Mapper extends AnswerMapper {

    @Override
    public Answer getAnswerFromDTO() {
        Boolean reply = mapJsonReply(replyAsJson);

        AnswerT2 answerT2 = new AnswerT2();
        answerT2.setType(Type.TYPE2);
        answerT2.setUserID(userID);
        answerT2.setReply(reply);

        return answerT2;
    }

    private Boolean mapJsonReply(JsonNode replyAsJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.treeToValue(replyAsJson, Boolean.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
