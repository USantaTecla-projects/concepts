package com.example.backend.api.resources.knowledge.justification;

import com.example.backend.api.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.api.resources.knowledge.definition.exception.model.DefinitionNotFoundException;
import com.example.backend.api.resources.knowledge.definition.model.Definition;
import com.example.backend.api.resources.knowledge.justification.dto.JustificationDTO;
import com.example.backend.api.resources.knowledge.justification.exception.model.JustificationDTOBadRequestException;
import com.example.backend.api.resources.knowledge.justification.exception.model.JustificationErrorNotProvidedException;
import com.example.backend.api.resources.knowledge.justification.exception.model.JustificationNotBelongToDefinitionException;
import com.example.backend.api.resources.knowledge.justification.exception.model.JustificationNotFoundException;
import com.example.backend.api.resources.knowledge.justification.model.Justification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JustificationService {

    private final DefinitionRepository definitionRepository;
    private final JustificationRepository justificationRepository;


    public JustificationService(
            DefinitionRepository definitionRepository,
            JustificationRepository justificationRepository) {
        this.definitionRepository = definitionRepository;
        this.justificationRepository = justificationRepository;
    }

    /**
     * Create a new justification by a given DTO (if it is correct), in the given definition.
     *
     * @param conceptId        The concept ID to keep a reference to it.
     * @param definition           The definition where to store the justification.
     * @param justificationDTO The data object to create a new justification.
     * @return The created justification.
     * @author Cristian
     */
    public Justification create(
            final Long conceptId,
            final Definition definition,
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
                .save(new Justification(textFromDTO, isCorrectFromDTO, errorFromDTO, conceptId, definition.getId()));

        definition.addJustification(justification);
        definitionRepository.save(definition);


        return justification;
    }

    /**
     * Find a justification in the database. If the justification does not exist
     * or does not belong to the definition, it throws an exception.
     *
     * @param definition          The definition where the justification should be.
     * @param justificationId The justification ID to look for.
     * @return The justification that match the ID an is in the definition.
     * @author Cristian
     */
    public Justification findOne(Definition definition, Long justificationId) {

        Justification justification = justificationRepository
                .findById(justificationId)
                .orElseThrow(
                        () -> new JustificationNotFoundException("The justification with id = " + justificationId + " has not been found"));

        if (!definition.containsJustification(justification))
            throw new JustificationNotBelongToDefinitionException(
                    "The justification with id = " + justificationId + " doesn't belong to the definition with id = " + definition.getId()
            );

        return justification;
    }

    /**
     * Find all the justifications in the given definition.
     *
     * @param definition The definition where to look for justifications.
     * @return A list of all the justifications stored in the definition.
     * @author Cristian
     */
    public List<Justification> findAll(Definition definition) {
        return Optional
                .ofNullable(definition.getJustificationList())
                .orElseThrow(() -> new DefinitionNotFoundException(
                        "The definition with id = " + definition.getId() + " has no justifications"
                ));
    }

    /**
     * Update the data of the definition by the given ID, with the given DTO, in the given definition.
     *
     * @param definition           The definition where the justification should be.
     * @param justificationId  The justification ID to look for.
     * @param justificationDTO The data object to update the justification.
     * @author Cristian
     */
    public void updateOne(
            Definition definition,
            Long justificationId,
            JustificationDTO justificationDTO) {

        Justification justification = findOne(definition, justificationId);

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
     * Remove one justification by the given ID in the given definition.
     *
     * @param definition          The definition where the justification should be.
     * @param justificationId The justification ID to look for.
     * @author Cristian
     */
    public void removeOne(Definition definition, Long justificationId) {
        Justification justification = findOne(definition, justificationId);
        definition.removeJustification(justification);

        definitionRepository.save(definition);
        justificationRepository.delete(justification);

    }


}
