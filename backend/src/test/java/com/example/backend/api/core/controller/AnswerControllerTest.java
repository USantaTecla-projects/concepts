package com.example.backend.api.core.controller;

import com.example.backend.api.core.answer.AnswerController;
import com.example.backend.api.core.answer.IAnswerService;
import com.example.backend.api.core.answer.dto.AnswerReqDTO;
import com.example.backend.api.core.answer.dto.AnswerResDTO;
import com.example.backend.api.core.answer.exception.model.AnswerDTOBadRequestException;
import com.example.backend.api.core.answer.exception.model.AnswerNotBelongToConceptException;
import com.example.backend.api.core.answer.exception.model.AnswerNotFoundException;
import com.example.backend.api.core.answer.model.Answer;
import com.example.backend.api.core.answer.util.AnswerAssembler;
import com.example.backend.api.core.concept.IConceptService;
import com.example.backend.api.core.concept.model.Concept;
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
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnswerController.class)
public class AnswerControllerTest {

    public final static long CONCEPT_ID = 1L;
    public final String BASE_URL = "/concepts/" + CONCEPT_ID + "/answers/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnswerAssembler answerAssembler;

    @MockBean
    private JustificationAssembler justificationAssembler;

    @MockBean
    private IAnswerService answerService;

    @MockBean
    private IConceptService conceptService;


    @Nested
    @DisplayName("POST")
    class AnswerPost {
        @Test
        @DisplayName("(Create) Should get 201 if the DTO is correct")
        void createWithCorrectDTO() throws Exception {
            final AnswerReqDTO answerReqDTO = new AnswerReqDTO("Software answer", true);
            final Answer answer = new Answer(2L, "Software answer", true, CONCEPT_ID, Collections.emptyList());
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.create(concept, answerReqDTO))
                    .thenReturn(answer);

            when(answerAssembler.toModel(any(Answer.class)))
                    .thenReturn(
                            EntityModel.of(
                                    new AnswerResDTO(answer.getId(), answer.getText(), answer.getCorrect()),
                                    linkTo(methodOn(AnswerController.class).findOne(answer.getConceptId(), answer.getId())).withSelfRel(),
                                    linkTo(methodOn(AnswerController.class).findAll(answer.getConceptId())).withRel("answers")));

            final String answerJsonDTO = mapObjectToJson(answerReqDTO);

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(answerJsonDTO))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("text", Matchers.is("Software answer")))
                    .andExpect(jsonPath("id").exists())
                    .andExpect(jsonPath("isCorrect", Matchers.is(true)))
                    .andExpect(jsonPath("_links.self").exists())
                    .andExpect(jsonPath("_links.answers").exists());
        }

        @Test
        @DisplayName("Create) Should get 400 if the DTO is malformed")
        void createWithWrongDTO() throws Exception {
            final AnswerReqDTO wrongConceptDTO = new AnswerReqDTO();
            final Answer answer = new Answer(2L, "Software answer", true, CONCEPT_ID);
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.create(concept, wrongConceptDTO))
                    .thenThrow(new AnswerDTOBadRequestException("Field text in Answer DTO is mandatory"));

            final String conceptJsonDTO = mapObjectToJson(wrongConceptDTO);

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(conceptJsonDTO))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("GET")
    class AnswerGet {
        @Test
        @DisplayName("(FindOne) Should get 200 if the answer exists on the concept answers list")
        void findOneWhenExists() throws Exception {
            final AnswerReqDTO answerReqDTO = new AnswerReqDTO("Software answer", true);
            final Answer answer = new Answer(2L, "Software answer", true, CONCEPT_ID);
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.findOne(concept, answer.getId()))
                    .thenReturn(answer);

            when(answerAssembler.toModel(any(Answer.class)))
                    .thenReturn(
                            EntityModel.of(
                                    new AnswerResDTO(answer.getId(), answer.getText(), answer.getCorrect()),
                                    linkTo(methodOn(AnswerController.class).findOne(answer.getConceptId(), answer.getId())).withSelfRel(),
                                    linkTo(methodOn(AnswerController.class).findAll(answer.getConceptId())).withRel("answers")));


            final String conceptJsonDTO = mapObjectToJson(answerReqDTO);

            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(conceptJsonDTO));

            mockMvc.perform(get(BASE_URL + answer.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("id").exists())
                    .andExpect(jsonPath("text", Matchers.is(answer.getText())))
                    .andExpect(jsonPath("_links.self").exists())
                    .andExpect(jsonPath("_links.answers").exists());
        }

        @Test
        @DisplayName("(FindOne) Should get 404 if the given id does not match in the database")
        void findOneWhenNotExists() throws Exception {
            final Answer answer = new Answer(2L, "Software answer", true, CONCEPT_ID);
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));
            final long wrongAnswerId = 99;

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.findOne(concept, wrongAnswerId))
                    .thenThrow(new AnswerNotFoundException("The answer with id = " + answer.getId() + " has not been found"));

            mockMvc.perform(get(BASE_URL + wrongAnswerId))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("(FindOne) Should get 404 if the given id does not exists on the concept answers list")
        void findOneWhenNotBelongsToConcept() throws Exception {
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>());
            final Answer answer = new Answer(2L, "Software answer", true, 3L);

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.findOne(concept, answer.getId()))
                    .thenThrow(new AnswerNotBelongToConceptException(
                            "The answer with id = " + answer.getId() + " doesn't belong to the concept with id = " + concept.getId()
                    ));


            mockMvc.perform(get(BASE_URL + answer.getId()))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("(FindAll) Should get 200 if the concept have answers in its answers list")
        void findAllWhenDataExists() throws Exception {
            final Answer answer1 = new Answer(2L, "Software answer", true, CONCEPT_ID);
            final Answer answer2 = new Answer(4L, "Hardware answer", true, CONCEPT_ID);
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer1, answer2)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.findAll(concept))
                    .thenReturn(concept.getAnswers());

            when(answerAssembler.toCollectionModel(concept.getAnswers()))
                    .thenReturn(
                            CollectionModel.of(
                                    concept.getAnswers()
                                            .stream()
                                            .map(answer -> EntityModel.of(new AnswerResDTO(answer.getId(), answer.getText(), answer.getCorrect())))
                                            .toList())
                    );

            mockMvc.perform(get(BASE_URL))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$._embedded").exists())
                    .andExpect(jsonPath("$._embedded.answerResDTOList.size()", Matchers.is(concept.getAnswers().size())));
        }

        @Test
        @DisplayName("(FindAll) Should get 404 if the concepts answers list is empty")
        void findAllWhenDataNotExists() throws Exception {
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>());

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.findAll(concept))
                    .thenThrow(new AnswerNotFoundException("The concept with id = " + concept.getId() + " has no answers"));

            mockMvc.perform(get("/concepts/" + concept.getId() + "/answers/" + "?page=1"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("PUT")
    class AnswerPut {
        @Test
        @DisplayName("(UpdateOne) Should get 204 if the Answer is in the concept answers list")
        void updateWhenExists() throws Exception {
            final AnswerReqDTO answerReqDTO = new AnswerReqDTO("Software answer", true);
            final Answer answer = new Answer(2L, "Software answer", true, CONCEPT_ID);
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            String answerJsonDTO = mapObjectToJson(answerReqDTO);

            mockMvc.perform(put("/concepts/" + concept.getId() + "/answers/" + answer.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(answerJsonDTO))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("(UpdateOne) Should get 404 if the Answer is not in the database")
        void updateWhenNotExists() throws Exception {
            final AnswerReqDTO answerReqDTO = new AnswerReqDTO("Software answer", true);
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>());
            final long wrongAnswerId = 99L;

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            String answerJsonDTO = mapObjectToJson(answerReqDTO);

            doThrow(new AnswerNotFoundException("The answer with id = " + wrongAnswerId + " has not been found"))
                    .when(answerService).updateOne(concept, wrongAnswerId, answerReqDTO);

            mockMvc.perform(put("/concepts/" + concept.getId() + "/answers/" + wrongAnswerId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(answerJsonDTO))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("(UpdateOne) Should get 404 if the answer does not belong to the concept answers list")
        void updateWhenNotBelongsToConcept() throws Exception {
            final AnswerReqDTO answerReqDTO = new AnswerReqDTO("Software answer", true);
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>());
            final long wrongAnswerId = 99L;

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            String answerJsonDTO = mapObjectToJson(answerReqDTO);

            doThrow(
                    new AnswerNotBelongToConceptException(
                            "The answer with id = " + wrongAnswerId + " doesn't belong to the concept with id = " + concept.getId())
            )
                    .when(answerService).updateOne(concept, wrongAnswerId, answerReqDTO);

            mockMvc.perform(put("/concepts/" + concept.getId() + "/answers/" + wrongAnswerId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(answerJsonDTO))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("DELETE")
    class AnswerDelete {
        @Test
        @DisplayName("(RemoveOne) Should get 204 if the answer is in the concept answers list")
        void deleteWhenExists() throws Exception {
            final Answer answer = new Answer(2L, "Software answer", true, CONCEPT_ID);
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));
            final long answerId = 1;

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            mockMvc.perform(delete(BASE_URL + answerId))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("(RemoveOne) Should get 404 if the answer is not in the database")
        void deleteWhenNotExists() throws Exception {
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>());
            final long wrongAnswerId = 99L;

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            doThrow(new AnswerNotFoundException("The answer with id = " + wrongAnswerId + " has not been found"))
                    .when(answerService).removeOne(concept, wrongAnswerId);

            mockMvc.perform(delete(BASE_URL + wrongAnswerId))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("(RemoveOne) Should get 404 if the answer does not belong to the concept answers list")
        void deleteWhenNotBelongsToConcept() throws Exception {
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>());
            final long wrongAnswerId = 99L;

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            doThrow(
                    new AnswerNotBelongToConceptException(
                            "The answer with id = " + wrongAnswerId + " doesn't belong to the concept with id = " + concept.getId())
            )
                    .when(answerService).removeOne(concept, wrongAnswerId);

            mockMvc.perform(delete(BASE_URL + wrongAnswerId))
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
