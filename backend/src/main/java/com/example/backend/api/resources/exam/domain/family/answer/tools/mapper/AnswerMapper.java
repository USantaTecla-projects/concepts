package com.example.backend.api.resources.exam.domain.family.answer.tools.mapper;

import com.example.backend.api.resources.exam.domain.family.answer.exception.specific.AnswerDTOBadRequestException;
import com.example.backend.api.resources.exam.domain.family.answer.model.Answer;
import com.example.backend.api.resources.exam.domain.family.answer.model.CorrectionStatus;
import com.example.backend.api.resources.exam.domain.family.answer.dto.AnswerDTO;
import com.fasterxml.jackson.databind.JsonNode;

public abstract class AnswerMapper {

    private AnswerDTO answerDTO;

    protected Long id;

    protected Long userID;

    protected Long questionID;

    protected CorrectionStatus correctionStatus;

    protected JsonNode replyAsJson;

    public Answer mapAnswer(AnswerDTO answerDTO) {
        this.answerDTO = answerDTO;
        setId();
        setUserID();
        setQuestionID();
        setCorrectionStatus();
        setReplyAsJson();
        return getAnswerFromDTO();
    }

    protected abstract Answer getAnswerFromDTO();

    private void setId() {
        id = answerDTO
                .getIDOptional(answerDTO.getId())
                .orElse(-1L);
    }

    private void setUserID() {
        userID = answerDTO
                .getUserIDOptional(answerDTO.getUserID())
                .orElseThrow(() -> new AnswerDTOBadRequestException("The field userID on AnswerDTO is mandatory"));
    }

    private void setQuestionID() {
        questionID = answerDTO
                .getQuestionIDOptional(answerDTO.getQuestionID())
                .orElseThrow(() -> new AnswerDTOBadRequestException("The field questionID on AnswerDTO is mandatory"));
    }

    private void setCorrectionStatus() {
        correctionStatus = answerDTO
                .getCorrectionStatusOptional(answerDTO.getCorrectionStatus())
                .orElseThrow(() -> new AnswerDTOBadRequestException("The field correctionStatus on AnswerDTO is mandatory"));
    }


    private void setReplyAsJson() {
        replyAsJson = answerDTO
                .getReplyOptional(answerDTO.getReply())
                .orElseThrow(() -> new AnswerDTOBadRequestException("The field reply on AnswerDTO is mandatory"));
    }

}
