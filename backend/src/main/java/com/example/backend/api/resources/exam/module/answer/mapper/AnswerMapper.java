package com.example.backend.api.resources.exam.module.answer.mapper;

import com.example.backend.api.resources.exam.module.answer.dto.AnswerDTO;
import com.example.backend.api.resources.exam.module.answer.exception.specific.AnswerDTOBadRequestException;
import com.example.backend.api.resources.exam.module.answer.model.Answer;
import com.fasterxml.jackson.databind.JsonNode;

public abstract class AnswerMapper {

    private AnswerDTO answerDTO;

    protected Long userID;

    protected JsonNode replyAsJson;

    public Answer mapAnswer(AnswerDTO answerDTO) {
        this.answerDTO = answerDTO;
        setUserID();
        setReplyAsJson();
        return getAnswerFromDTO();
    }

    protected abstract Answer getAnswerFromDTO();

    private void setUserID() {
        userID = answerDTO
                .getUserIDOptional(answerDTO.getUserID())
                .orElseThrow(() -> new AnswerDTOBadRequestException("The field userId on AnswerDTO is mandatory"));
    }

    private void setReplyAsJson() {
        replyAsJson = answerDTO
                .getReplyOptional(answerDTO.getReply())
                .orElseThrow(() -> new AnswerDTOBadRequestException("The field reply on AnswerDTO is mandatory"));
    }
}
