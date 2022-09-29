package com.example.backend.api.resources.exam.module.answer.mapper.specific;

import com.example.backend.api.resources.exam.module.answer.mapper.AnswerMapper;
import com.example.backend.api.resources.exam.module.answer.model.Answer;
import com.example.backend.api.resources.exam.module.answer.model.specific.AnswerT3;
import com.example.backend.api.resources.exam.module.type.Type;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AnswerT3Mapper extends AnswerMapper {

    @Override
    public Answer getAnswerFromDTO() {
        Boolean reply = mapJsonReply(replyAsJson);

        AnswerT3 answerT3 = new AnswerT3();
        answerT3.setType(Type.TYPE3);
        answerT3.setUserID(userID);
        answerT3.setReply(reply);

        return answerT3;
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
