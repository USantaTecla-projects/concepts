package com.example.backend.api.resources.knowledge.justification;

import com.example.backend.api.resources.knowledge.answer.AnswerRepository;
import com.example.backend.api.resources.knowledge.answer.exception.model.AnswerNotFoundException;
import com.example.backend.api.resources.knowledge.answer.model.Answer;
import com.example.backend.api.resources.knowledge.justification.dto.JustificationDTO;
import com.example.backend.api.resources.knowledge.justification.exception.model.JustificationDTOBadRequestException;
import com.example.backend.api.resources.knowledge.justification.exception.model.JustificationErrorNotProvidedException;
import com.example.backend.api.resources.knowledge.justification.exception.model.JustificationNotBelongToAnswerException;
import com.example.backend.api.resources.knowledge.justification.exception.model.JustificationNotFoundException;
import com.example.backend.api.resources.knowledge.justification.model.Justification;
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

    /**
     * Create a new justification by a given DTO (if it is correct), in the given answer.
     *
     * @param conceptId        The concept ID to keep a reference to it.
     * @param answer           The answer where to store the justification.
     * @param justificationDTO The data object to create a new justification.
     * @return The created justification.
     * @author Cristian
     */
    public Justification create(
            final Long conceptId,
            final Answer answer,
            final JustificationDTO justificationDTO) {
        String textFromDTO = justificationDTO
                .getTextOptional(justificationDTO.getText())
                .orElseThrow(() -> new JustificationDTOBadRequestException("Field text in Justification DTO is mandatory"));

        Boolean isCorrectFromDTO = justificationDTO
                .getCorrectOptional(justificationDTO.getCorrect())
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

    /**
     * Find a justification in the database. If the justification does not exist
     * or does not belong to the answer, it throws an exception.
     *
     * @param answer          The answer where the justification should be.
     * @param justificationId The justification ID to look for.
     * @return The justification that match the ID an is in the answer.
     * @author Cristian
     */
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

    /**
     * Find all the justifications in the given answer.
     *
     * @param answer The answer where to look for justifications.
     * @return A list of all the justifications stored in the answer.
     * @author Cristian
     */
    public List<Justification> findAll(Answer answer) {
        return Optional
                .ofNullable(answer.getJustifications())
                .orElseThrow(() -> new AnswerNotFoundException(
                        "The answer with id = " + answer.getId() + " has no justifications"
                ));
    }

    /**
     * Update the data of the answer by the given ID, with the given DTO, in the given answer.
     *
     * @param answer           The answer where the justification should be.
     * @param justificationId  The justification ID to look for.
     * @param justificationDTO The data object to update the justification.
     * @author Cristian
     */
    public void updateOne(
            Answer answer,
            Long justificationId,
            JustificationDTO justificationDTO) {

        Justification justification = findOne(answer, justificationId);

        String textFromDTO = justificationDTO
                .getTextOptional(justificationDTO.getText())
                .orElse(justification.getText());

        Boolean isCorrectFromDTO = justificationDTO
                .getCorrectOptional(justificationDTO.getCorrect())
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

    /**
     * Remove one justification by the given ID in the given answer.
     *
     * @param answer          The answer where the justification should be.
     * @param justificationId The justification ID to look for.
     * @author Cristian
     */
    public void removeOne(Answer answer, Long justificationId) {
        Justification justification = findOne(answer, justificationId);
        answer.removeJustification(justification);

        answerRepository.save(answer);
        justificationRepository.delete(justification);

    }


}
