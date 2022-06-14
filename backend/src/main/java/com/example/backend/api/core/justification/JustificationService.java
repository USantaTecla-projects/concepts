package com.example.backend.api.core.justification;

import com.example.backend.api.core.answer.AnswerRepository;
import com.example.backend.api.core.answer.exception.model.AnswerNotFoundException;
import com.example.backend.api.core.answer.model.Answer;
import com.example.backend.api.core.justification.dto.JustificationDTO;
import com.example.backend.api.core.justification.exception.model.JustificationDTOBadRequestException;
import com.example.backend.api.core.justification.exception.model.JustificationErrorNotProvidedException;
import com.example.backend.api.core.justification.exception.model.JustificationNotBelongToAnswerException;
import com.example.backend.api.core.justification.exception.model.JustificationNotFoundException;
import com.example.backend.api.core.justification.model.Justification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JustificationService {

    private final AnswerRepository answerRepository;
    private final JustificationRepository justificationRepository;


    public JustificationService(
            AnswerRepository answerRepository,
            JustificationRepository justificationRepository) {
        this.answerRepository = answerRepository;
        this.justificationRepository = justificationRepository;
    }

    public Justification create(
            final Long conceptId,
            final Answer answer,
            final JustificationDTO justificationDTO) {
        String textFromDTO = justificationDTO
                .getTextOptional(justificationDTO.getText())
                .orElseThrow(() -> new JustificationDTOBadRequestException("Field text in Justification DTO is mandatory"));

        Boolean isCorrectFromDTO = justificationDTO
                .getCorrectOptional(justificationDTO.getIsCorrect())
                .orElseThrow(() -> new JustificationDTOBadRequestException("Field isCorrect in Justification DTO is mandatory"));

        String errorFromDTO = justificationDTO
                .getErrorOptional(justificationDTO.getError())
                .orElseGet(() -> {
                    if (!isCorrectFromDTO)
                        throw new JustificationDTOBadRequestException("Field error in Justification DTO is mandatory");

                    return null;
                });

        Justification justification = justificationRepository
                .save(new Justification(textFromDTO, isCorrectFromDTO, errorFromDTO, conceptId, answer.getId()));

        answer.addJustification(justification);
        answerRepository.save(answer);

        return justification;
    }

    public Justification findOne(Answer answer, Long justificationId) {

        Justification justification = justificationRepository
                .findById(justificationId)
                .orElseThrow(
                        () -> new JustificationNotFoundException("The justification with id = " + justificationId + " has not been found"));

        if (!answer.containsJustification(justification))
            throw new JustificationNotBelongToAnswerException(
                    "The justification with id = " + justificationId + " doesn't belong to the answer with id = " + answer.getId()
            );

        return justification;
    }

    public List<Justification> findAll(Answer answer) {
        return Optional
                .ofNullable(answer.getJustifications())
                .orElseThrow(() -> new AnswerNotFoundException(
                        "The answer with id = " + answer.getId() + " has no justifications"
                ));
    }

    public void updateOne(
            Answer answer,
            Long justificationId,
            JustificationDTO justificationDTO) {

        Justification justification = findOne(answer, justificationId);

        String textFromDTO = justificationDTO
                .getTextOptional(justificationDTO.getText())
                .orElse(justification.getText());

        Boolean isCorrectFromDTO = justificationDTO
                .getCorrectOptional(justificationDTO.getIsCorrect())
                .orElse(justification.getCorrect());

        String errorFromDTO = justificationDTO
                .getErrorOptional(justificationDTO.getError())
                .orElse(justification.getError());

        if (!isCorrectFromDTO && errorFromDTO == null)
            throw new JustificationErrorNotProvidedException("An error should be provided");

        justification.setText(textFromDTO);
        justification.setCorrect(isCorrectFromDTO);
        justification.setError(isCorrectFromDTO ? null : errorFromDTO);

        justificationRepository.save(justification);
    }

    public void removeOne(Answer answer, Long justificationId) {
        Justification justification = findOne(answer, justificationId);
        answer.removeJustification(justification);

        answerRepository.save(answer);
        justificationRepository.delete(justification);
    }


}
