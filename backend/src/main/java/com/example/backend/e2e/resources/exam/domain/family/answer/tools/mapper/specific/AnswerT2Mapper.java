package com.example.backend.e2e.resources.exam.domain.family.answer.tools.mapper.specific;

import com.example.backend.e2e.resources.exam.domain.family.answer.exception.specific.AnswerNotFoundException;
import com.example.backend.e2e.resources.exam.domain.family.answer.tools.mapper.AnswerMapper;
import com.example.backend.e2e.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.e2e.resources.exam.domain.family.answer.model.specific.AnswerT2;
import com.example.backend.e2e.resources.exam.domain.factory.Type;
import com.example.backend.e2e.resources.exam.domain.family.answer.repository.AnswerT2Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AnswerT2Mapper extends AnswerMapper {

    private final AnswerT2Repository answerT2Repository;

    public AnswerT2Mapper(AnswerT2Repository answerT2Repository) {
        this.answerT2Repository = answerT2Repository;
    }

    @Override
    public Answer getAnswerFromDTO() {
        Boolean reply = mapJsonReply(replyAsJson);

        boolean answerAlreadyExist = answerT2Repository.existsById(id);
        if(!answerAlreadyExist){
            AnswerT2 answerT2 = new AnswerT2();
            updateAnswerInfo(reply, answerT2);

            return answerT2;
        }

        AnswerT2 answerT2 = answerT2Repository
                .findById(id)
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found"));

        updateAnswerInfo(reply, answerT2);

        return answerT2;
    }

    private void updateAnswerInfo(Boolean reply, AnswerT2 answerT2) {
        answerT2.setType(Type.TYPE2);
        answerT2.setUserID(userID);
        answerT2.setQuestionID(questionID);
        answerT2.setReply(reply);
        answerT2.setCorrectionStatus(correctionStatus);
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
