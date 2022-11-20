package com.example.backend.e2e.resources.knowledge.justification;

import com.example.backend.e2e.resources.knowledge.definition.DefinitionRepository;
import com.example.backend.e2e.resources.knowledge.definition.exception.specific.DefinitionNotFoundException;
import com.example.backend.e2e.resources.knowledge.definition.model.Definition;
import com.example.backend.e2e.resources.knowledge.justification.dto.JustificationDTO;
import com.example.backend.e2e.resources.knowledge.justification.exception.specific.JustificationDTOBadRequestException;
import com.example.backend.e2e.resources.knowledge.justification.exception.specific.JustificationErrorNotProvidedException;
import com.example.backend.e2e.resources.knowledge.justification.exception.specific.JustificationNotBelongToDefinitionException;
import com.example.backend.e2e.resources.knowledge.justification.exception.specific.JustificationNotFoundException;
import com.example.backend.e2e.resources.knowledge.justification.model.Justification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CrudJustificationService {

    private final DefinitionRepository definitionRepository;

    private final JustificationRepository justificationRepository;

    public CrudJustificationService(
            DefinitionRepository definitionRepository,
            JustificationRepository justificationRepository) {
        this.definitionRepository = definitionRepository;
        this.justificationRepository = justificationRepository;
    }

    public Justification createOne(
            final Long conceptID,
            final Definition definition,
            final JustificationDTO justificationDTO
    ) {
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
                .save(new Justification(textFromDTO, isCorrectFromDTO, errorFromDTO, conceptID, definition.getId()));

        definition.addJustification(justification);
        definitionRepository.save(definition);


        return justification;
    }
    
    public Justification findOne(Definition definition, Long justificationID) {

        Justification justification = justificationRepository
                .findById(justificationID)
                .orElseThrow(
                        () -> new JustificationNotFoundException("The justification with id = " + justificationID + " has not been found"));

        if (!definition.containsJustification(justification))
            throw new JustificationNotBelongToDefinitionException(
                    "The justification with id = " + justificationID + " doesn't belong to the definition with id = " + definition.getId()
            );

        return justification;
    }

    public List<Justification> findAll(Definition definition) {
        return Optional
                .ofNullable(definition.getJustificationList())
                .orElseThrow(() -> new DefinitionNotFoundException(
                        "The definition with id = " + definition.getId() + " has no justifications"
                ));
    }

    public void updateOne(
            Definition definition,
            Long justificationID,
            JustificationDTO justificationDTO) {

        Justification justification = findOne(definition, justificationID);

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

    public void removeOne(Definition definition, Long justificationID) {
        Justification justification = findOne(definition, justificationID);
        definition.removeJustification(justification);

        definitionRepository.save(definition);
        justificationRepository.delete(justification);

    }
}
