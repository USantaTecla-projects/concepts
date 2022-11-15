package com.example.backend.api.resources.exam.domain.family.answer.tools.mapper.specific;

import com.example.backend.api.resources.exam.domain.factory.Type;
import com.example.backend.api.resources.exam.domain.family.answer.exception.specific.AnswerNotFoundException;
import com.example.backend.api.resources.exam.domain.family.answer.tools.mapper.AnswerMapper;
import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.api.resources.exam.domain.family.answer.model.specific.AnswerT0;
import com.example.backend.api.resources.exam.domain.family.answer.repository.AnswerT0Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AnswerT0Mapper extends AnswerMapper {


    private final AnswerT0Repository answerT0Repository;

    public AnswerT0Mapper(AnswerT0Repository answerT0Repository) {
        this.answerT0Repository = answerT0Repository;
    }

    @Override
    public Answer getAnswerFromDTO() {
        String reply = mapJsonReply(replyAsJson);

        boolean answerAlreadyExist = answerT0Repository.existsById(id);
        if(!answerAlreadyExist){
            AnswerT0 answerT0 = new AnswerT0();
            updateAnswerInfo(reply, answerT0);

            return answerT0;
        }

        AnswerT0 answerT0 = answerT0Repository
                .findById(id)
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found"));

        updateAnswerInfo(reply, answerT0);

        return answerT0;
    }

    private void updateAnswerInfo(String reply, AnswerT0 answerT0) {
        answerT0.setType(Type.TYPE0);
        answerT0.setUserID(userID);
        answerT0.setQuestionID(questionID);
        answerT0.setReply(reply);
        answerT0.setCorrectionStatus(correctionStatus);
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
