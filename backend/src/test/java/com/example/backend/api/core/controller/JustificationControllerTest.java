package com.example.backend.api.core.controller;


import com.example.backend.api.core.answer.IAnswerService;
import com.example.backend.api.core.answer.model.Answer;
import com.example.backend.api.core.concept.IConceptService;
import com.example.backend.api.core.concept.model.Concept;
import com.example.backend.api.core.justification.IJustificationsService;
import com.example.backend.api.core.justification.JustificationController;
import com.example.backend.api.core.justification.dto.JustificationReqDTO;
import com.example.backend.api.core.justification.exception.model.JustificationDTOBadRequestException;
import com.example.backend.api.core.justification.exception.model.JustificationNotBelongToAnswerException;
import com.example.backend.api.core.justification.exception.model.JustificationNotFoundException;
import com.example.backend.api.core.justification.model.Justification;
import com.example.backend.api.core.justification.util.JustificationAssembler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JustificationController.class)
public class JustificationControllerTest {

    public final static long CONCEPT_ID = 1L;
    public final static long ANSWER_ID = 2L;
    public final static long JUSTIFICATION_ID = 3L;

    public static final String BASE_URL = "/concepts/" + CONCEPT_ID + "/answers/" + ANSWER_ID + "/justifications/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JustificationAssembler justificationAssembler;

    @MockBean
    private IAnswerService answerService;

    @MockBean
    private IConceptService conceptService;

    @MockBean
    private IJustificationsService justificationsService;

    @Nested
    @DisplayName("POST")
    class JustificationPost {

        @Test
        @DisplayName("(Create) Should get 201 if the DTO is correct")
        void createWithCorrectDTO() throws Exception {
            final JustificationReqDTO justificationReqDTO = new JustificationReqDTO("Software Justification", true, null);
            final Justification justification = new Justification(JUSTIFICATION_ID, "Software Justification", true, null, CONCEPT_ID, ANSWER_ID);
            final Answer answer = new Answer(ANSWER_ID, "Software answer", true, CONCEPT_ID, new LinkedList<>(List.of(justification)));
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.findOne(concept, answer.getId()))
                    .thenReturn(answer);

            when(justificationsService.create(concept.getId(), answer, justificationReqDTO))
                    .thenReturn(justification);

            when(justificationAssembler.toModel(any(Justification.class)))
                    .thenCallRealMethod();

            final String justificationJsonDTO = mapObjectToJson(justificationReqDTO);

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(justificationJsonDTO))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("text", Matchers.is(justification.getText())))
                    .andExpect(jsonPath("id").exists())
                    .andExpect(jsonPath("isCorrect", Matchers.is(justification.getCorrect())))
                    .andExpect(jsonPath("_links.self").exists())
                    .andExpect(jsonPath("_links.justifications").exists());

        }

        @Test
        @DisplayName("Create) Should get 400 if the DTO is malformed")
        void createWithWrongDTO() {
            final JustificationReqDTO justificationReqDTO = new JustificationReqDTO();
            final Justification justification = new Justification(JUSTIFICATION_ID, "Software Justification", true, null, CONCEPT_ID, ANSWER_ID);
            final Answer answer = new Answer(ANSWER_ID, "Software answer", true, CONCEPT_ID, new LinkedList<>(List.of(justification)));
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.findOne(concept, answer.getId()))
                    .thenReturn(answer);

            when(justificationsService.create(concept.getId(), answer, justificationReqDTO))
                    .thenThrow(new JustificationDTOBadRequestException("Field text in Justification DTO is mandatory"));

        }
    }

    @Nested
    @DisplayName("GET")
    class JustificationGet {
        @Test
        @DisplayName("(FindOne) Should get 200 if the justification exists on the answer justifications list")
        void findOneWhenExists() throws Exception {
            final Justification justification = new Justification(JUSTIFICATION_ID, "Software Justification", true, null, CONCEPT_ID, ANSWER_ID);
            final Answer answer = new Answer(ANSWER_ID, "Software answer", true, CONCEPT_ID, new LinkedList<>(List.of(justification)));
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.findOne(concept, answer.getId()))
                    .thenReturn(answer);

            when(justificationsService.findOne(answer, justification.getId()))
                    .thenReturn(justification);

            when(justificationAssembler.toModel(any(Justification.class)))
                    .thenCallRealMethod();

            mockMvc.perform(get(BASE_URL + justification.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("id").exists())
                    .andExpect(jsonPath("text", Matchers.is(justification.getText())))
                    .andExpect(jsonPath("_links.self").exists())
                    .andExpect(jsonPath("_links.justifications").exists());

        }

        @Test
        @DisplayName("(FindOne) Should get 404 if the given id does not match in the database")
        void findOneWhenNotExists() throws Exception {
            final Justification justification = new Justification(JUSTIFICATION_ID, "Software Justification", true, null, CONCEPT_ID, ANSWER_ID);
            final Answer answer = new Answer(ANSWER_ID, "Software answer", true, CONCEPT_ID, new LinkedList<>(List.of(justification)));
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));
            final long wrongJustificationId = 99L;

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.findOne(concept, answer.getId()))
                    .thenReturn(answer);

            when(justificationsService.findOne(answer, wrongJustificationId))
                    .thenThrow(new JustificationNotFoundException("The justification with id = " + justification.getId() + " has not been found"));

            mockMvc.perform(get(BASE_URL + wrongJustificationId))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("(FindOne) Should get 404 if the given id does not exists on the answer justifications list")
        void findOneWhenNotBelongsToConcept() throws Exception {
            final Justification justification = new Justification(JUSTIFICATION_ID, "Software Justification", true, null, CONCEPT_ID, ANSWER_ID);
            final Answer answer = new Answer(ANSWER_ID, "Software answer", true, CONCEPT_ID, new LinkedList<>(List.of(justification)));
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.findOne(concept, answer.getId()))
                    .thenReturn(answer);

            when(justificationsService.findOne(answer, justification.getId()))
                    .thenThrow(new JustificationNotBelongToAnswerException(
                            "The justification with id = " + justification.getId() + " doesn't belong to the answer with id = " + answer.getId()
                    ));

            mockMvc.perform(get(BASE_URL + justification.getId()))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("(FindAll) Should get 200 if the answer have justifications in its justifications list")
        void findAllWhenDataExists() throws Exception {
            final Justification justification1 = new Justification(JUSTIFICATION_ID, "Software Justification", true, null, CONCEPT_ID, ANSWER_ID);
            final Justification justification2 = new Justification(4L, "Software Justification", true, null, CONCEPT_ID, ANSWER_ID);
            final Answer answer = new Answer(ANSWER_ID, "Software answer", true, CONCEPT_ID, new LinkedList<>(List.of(justification1, justification2)));
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));


            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.findOne(concept, answer.getId()))
                    .thenReturn(answer);

            when(justificationsService.findAll(answer))
                    .thenReturn(answer.getJustifications());

            when(justificationAssembler.toModel(any(Justification.class)))
                    .thenCallRealMethod();

            when(justificationAssembler.toCollectionModel(any()))
                    .thenCallRealMethod();

            mockMvc.perform(get(BASE_URL))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$._embedded").exists())
                    .andExpect(jsonPath("$._embedded.justificationResDTOList.size()", Matchers.is(answer.getJustifications().size())));

        }

        @Test
        @DisplayName("(FindAll) Should get 404 if the answers justifications list is empty")
        void findAllWhenDataNotExists() throws Exception {
            final Answer answer = new Answer(ANSWER_ID, "Software answer", true, CONCEPT_ID, new LinkedList<>());
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.findOne(concept, answer.getId()))
                    .thenReturn(answer);

            when(justificationsService.findAll(answer))
                    .thenThrow(new JustificationNotFoundException("The answer with id = " + answer.getId() + " has no justifications"));
        }
    }

    @Nested
    @DisplayName("PUT")
    class JustificationPut {
        @Test
        @DisplayName("(UpdateOne) Should get 204 if the justification is in the answer justifications list")
        void updateWhenExists() throws Exception {
            final JustificationReqDTO justificationReqDTO = new JustificationReqDTO("Software Justification", true, null);
            final Justification justification = new Justification(JUSTIFICATION_ID, "Software Justification", true, null, CONCEPT_ID, ANSWER_ID);
            final Answer answer = new Answer(ANSWER_ID, "Software answer", true, CONCEPT_ID, new LinkedList<>(List.of(justification)));
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.findOne(concept, answer.getId()))
                    .thenReturn(answer);

            String justificationJsonDTO = mapObjectToJson(justificationReqDTO);

            mockMvc.perform(put("/concepts/" + concept.getId() + "/answers/" + answer.getId() + "/justifications/" + justification.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(justificationJsonDTO))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("(UpdateOne) Should get 404 if the Answer is not in the database")
        void updateWhenNotExists() throws Exception {
            final JustificationReqDTO justificationReqDTO = new JustificationReqDTO("Software Justification", true, null);
            final Answer answer = new Answer(ANSWER_ID, "Software answer", true, CONCEPT_ID, new LinkedList<>(List.of()));
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));
            final long wrongJustificationId = 99L;

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.findOne(concept, answer.getId()))
                    .thenReturn(answer);

            doThrow(new JustificationNotFoundException("The justification with id = " + wrongJustificationId + " has not been found"))
                    .when(justificationsService).updateOne(answer, wrongJustificationId, justificationReqDTO);

            String justificationJsonDTO = mapObjectToJson(justificationReqDTO);

            mockMvc.perform(put("/concepts/" + concept.getId() + "/answers/" + answer.getId() + "/justifications/" + wrongJustificationId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(justificationJsonDTO))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("(UpdateOne) Should get 404 if the answer does not belong to the concept answers list")
        void updateWhenNotBelongsToConcept() throws Exception {
            final JustificationReqDTO justificationReqDTO = new JustificationReqDTO("Software Justification", true, null);
            final Justification justification = new Justification(JUSTIFICATION_ID, "Software Justification", true, null, CONCEPT_ID, ANSWER_ID);
            final Answer answer = new Answer(ANSWER_ID, "Software answer", true, CONCEPT_ID, new LinkedList<>(List.of()));
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.findOne(concept, answer.getId()))
                    .thenReturn(answer);

            doThrow(new JustificationNotBelongToAnswerException("The justification with id = " + justification.getId() + " doesn't belong to the answer with id = " + answer.getId()))
                    .when(justificationsService).updateOne(answer, justification.getId(), justificationReqDTO);

            String justificationJsonDTO = mapObjectToJson(justificationReqDTO);

            mockMvc.perform(put("/concepts/" + concept.getId() + "/answers/" + answer.getId() + "/justifications/" + justification.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(justificationJsonDTO))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("DELETE")
    class JustificationDelete {

        @Test
        @DisplayName("(RemoveOne) Should get 204 if the answer is in the concept answers list")
        void deleteWhenExists() throws Exception {
            final Justification justification = new Justification(JUSTIFICATION_ID, "Software Justification", true, null, CONCEPT_ID, ANSWER_ID);
            final Answer answer = new Answer(ANSWER_ID, "Software answer", true, CONCEPT_ID, new LinkedList<>(List.of(justification)));
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.findOne(concept, answer.getId()))
                    .thenReturn(answer);


            mockMvc.perform(delete(BASE_URL + justification.getId()))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("(RemoveOne) Should get 404 if the answer is not in the database")
        void deleteWhenNotExists() throws Exception {
            final Justification justification = new Justification(JUSTIFICATION_ID, "Software Justification", true, null, CONCEPT_ID, ANSWER_ID);
            final Answer answer = new Answer(ANSWER_ID, "Software answer", true, CONCEPT_ID, new LinkedList<>(List.of(justification)));
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));
            final long wrongJustificationId = 99L;

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.findOne(concept, answer.getId()))
                    .thenReturn(answer);


            doThrow(new JustificationNotFoundException("The justification with id = " + wrongJustificationId + " has not been found"))
                    .when(justificationsService).removeOne(answer, wrongJustificationId);

            mockMvc.perform(delete(BASE_URL + wrongJustificationId))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("(RemoveOne) Should get 404 if the answer does not belong to the concept answers list")
        void deleteWhenNotBelongsToConcept() throws Exception {
            final Justification justification = new Justification(JUSTIFICATION_ID, "Software Justification", true, null, CONCEPT_ID, ANSWER_ID);
            final Answer answer = new Answer(ANSWER_ID, "Software answer", true, CONCEPT_ID, new LinkedList<>());
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.findOne(concept, answer.getId()))
                    .thenReturn(answer);


            doThrow(
                    new JustificationNotBelongToAnswerException(
                            "The justification with id = " + justification.getId() + " doesn't belong to the concept with id = " + answer.getId())
            )
                    .when(justificationsService).removeOne(answer, justification.getId());

            mockMvc.perform(delete(BASE_URL + justification.getId()))
                    .andExpect(status().isNotFound());
        }

    }

    /**
     * Converts Object to JSON.
     *
     * @param object The object to be converted.
     * @return A String with the Object as JSON.
     * @throws JsonProcessingException Is thrown if the Object can't be parsed.
     */
    private String mapObjectToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(object);
    }
}
