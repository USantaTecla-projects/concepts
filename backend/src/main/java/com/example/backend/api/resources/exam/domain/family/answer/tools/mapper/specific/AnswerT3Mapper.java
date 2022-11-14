package com.example.backend.api.resources.exam.domain.family.answer.tools.mapper.specific;

import com.example.backend.api.resources.exam.domain.family.answer.exception.specific.AnswerNotFoundException;
import com.example.backend.api.resources.exam.domain.family.answer.tools.mapper.AnswerMapper;
import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.api.resources.exam.domain.family.answer.model.specific.AnswerT3;
import com.example.backend.api.resources.exam.domain.factory.Type;
import com.example.backend.api.resources.exam.domain.family.answer.repository.AnswerT3Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AnswerT3Mapper extends AnswerMapper {

    private final AnswerT3Repository answerT3Repository;

    public AnswerT3Mapper(AnswerT3Repository answerT3Repository) {
        this.answerT3Repository = answerT3Repository;
    }

    @Override
    public Answer getAnswerFromDTO() {
        Boolean reply = mapJsonReply(replyAsJson);

        boolean answerAlreadyExist = answerT3Repository.existsById(id);
        if(!answerAlreadyExist){
            AnswerT3 answerT3 = new AnswerT3();
            updateAnswerInfo(reply, answerT3);

            return answerT3;
        }

        AnswerT3 answerT3 = answerT3Repository
                .findById(id)
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found"));

        updateAnswerInfo(reply, answerT3);

        return answerT3;
    }

    private void updateAnswerInfo(Boolean reply, AnswerT3 answerT3) {
        answerT3.setType(Type.TYPE3);
        answerT3.setUserID(userID);
        answerT3.setReply(reply);
        answerT3.setCorrectionStatus(correctionStatus);
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
