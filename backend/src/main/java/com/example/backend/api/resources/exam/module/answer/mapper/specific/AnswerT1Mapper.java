package com.example.backend.api.resources.exam.module.answer.mapper.specific;

import com.example.backend.api.resources.exam.module.answer.mapper.AnswerMapper;
import com.example.backend.api.resources.exam.module.answer.model.Answer;
import com.example.backend.api.resources.exam.module.answer.model.specific.AnswerT1;
import com.example.backend.api.resources.exam.module.type.Type;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AnswerT1Mapper extends AnswerMapper {

    @Override
    public Answer getAnswerFromDTO() {
        String reply = mapJsonReply(replyAsJson);

        AnswerT1 answerT1 = new AnswerT1();
        answerT1.setType(Type.TYPE1);
        answerT1.setUserID(userID);
        answerT1.setReply(reply);

        return answerT1;
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
