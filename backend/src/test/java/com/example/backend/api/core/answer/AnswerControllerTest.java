package com.example.backend.api.core.answer;

import com.example.backend.api.resources.auth.configuration.AuthConfiguration;
import com.example.backend.api.resources.auth.jwt.components.JwtRequestFilter;
import com.example.backend.api.resources.auth.jwt.components.JwtTokenProvider;
import com.example.backend.api.resources.auth.util.UserDetailsFinder;
import com.example.backend.api.resources.knowledge.answer.AnswerController;
import com.example.backend.api.resources.knowledge.answer.AnswerService;
import com.example.backend.api.resources.knowledge.answer.dto.AnswerDTO;
import com.example.backend.api.resources.knowledge.answer.exception.model.AnswerDTOBadRequestException;
import com.example.backend.api.resources.knowledge.answer.exception.model.AnswerNotBelongToConceptException;
import com.example.backend.api.resources.knowledge.answer.exception.model.AnswerNotFoundException;
import com.example.backend.api.resources.knowledge.answer.model.Answer;
import com.example.backend.api.resources.knowledge.concept.ConceptService;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import com.example.backend.api.resources.user.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.example.backend.util.MapObjectToJson.mapObjectToJson;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnswerController.class)
@Import({
        AuthConfiguration.class,
        UserDetailsFinder.class,
        JwtRequestFilter.class,
        JwtTokenProvider.class
})
@WithMockUser(username = "teacher", roles = "TEACHER")
public class AnswerControllerTest {

    public final static long CONCEPT_ID = 1L;
    public final static long ANSWER_ID = 2L;
    public final String BASE_URL = "/concepts/" + CONCEPT_ID + "/answers/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnswerService answerService;

    @MockBean
    private ConceptService conceptService;

    @MockBean
    private UserRepository userRepository;


    @Nested
    @DisplayName("POST")
    class AnswerPost {
        @Test
        @DisplayName("(Create) Should get 201 if the DTO is correct")
        void createWithCorrectDTO() throws Exception {
            final AnswerDTO answerDTO = new AnswerDTO("Software answer", true);
            final Answer answer = new Answer(ANSWER_ID, answerDTO.getText(), answerDTO.getCorrect(), CONCEPT_ID, Collections.emptyList());
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.create(concept, answerDTO))
                    .thenReturn(answer);

            final String answerJsonDTO = mapObjectToJson(answerDTO);

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(answerJsonDTO))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("text", Matchers.is(answer.getText())))
                    .andExpect(jsonPath("id").exists())
                    .andExpect(jsonPath("correct", Matchers.is(answer.getCorrect())));
        }

        @Test
        @DisplayName("Create) Should get 400 if the DTO is malformed")
        void createWithWrongDTO() throws Exception {
            final AnswerDTO wrongConceptDTO = new AnswerDTO();
            final Answer answer = new Answer(ANSWER_ID, "Software answer", true, CONCEPT_ID);
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
            final Answer answer = new Answer(ANSWER_ID, "Software answer", true, CONCEPT_ID);
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.findOne(concept, answer.getId()))
                    .thenReturn(answer);


            mockMvc.perform(get(BASE_URL + answer.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("id").exists())
                    .andExpect(jsonPath("text", Matchers.is(answer.getText())));
        }

        @Test
        @DisplayName("(FindOne) Should get 404 if the given id does not match in the database")
        void findOneWhenNotExists() throws Exception {
            final Answer answer = new Answer(ANSWER_ID, "Software answer", true, CONCEPT_ID);
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));
            final long wrongAnswerId = 99L;

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
            final Answer answer = new Answer(ANSWER_ID, "Software answer", true, 3L);

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
            final Answer answer1 = new Answer(ANSWER_ID, "Software answer", true, CONCEPT_ID);
            final Answer answer2 = new Answer(3L, "Hardware answer", true, CONCEPT_ID);
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer1, answer2)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.findAll(concept))
                    .thenReturn(concept.getAnswerList());


            mockMvc.perform(get(BASE_URL))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray());
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
        @DisplayName("(UpdateOne) Should get 204 if the justification is in the concept answers list")
        void updateWhenExists() throws Exception {
            final AnswerDTO answerDTO = new AnswerDTO("Software answer", true);
            final Answer answer = new Answer(ANSWER_ID, answerDTO.getText(), answerDTO.getCorrect(), CONCEPT_ID);
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            String answerJsonDTO = mapObjectToJson(answerDTO);

            mockMvc.perform(put("/concepts/" + concept.getId() + "/answers/" + answer.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(answerJsonDTO))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("(UpdateOne) Should get 404 if the Answer is not in the database")
        void updateWhenNotExists() throws Exception {
            final AnswerDTO answerDTO = new AnswerDTO("Software answer", true);
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>());
            final long wrongAnswerId = 99L;

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            doThrow(new AnswerNotFoundException("The answer with id = " + wrongAnswerId + " has not been found"))
                    .when(answerService).updateOne(concept, wrongAnswerId, answerDTO);

            String answerJsonDTO = mapObjectToJson(answerDTO);

            mockMvc.perform(put("/concepts/" + concept.getId() + "/answers/" + wrongAnswerId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(answerJsonDTO))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("(UpdateOne) Should get 404 if the answer does not belong to the concept answers list")
        void updateWhenNotBelongsToConcept() throws Exception {
            final AnswerDTO answerDTO = new AnswerDTO("Software answer", true);
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>());
            final Answer answer = new Answer(ANSWER_ID, answerDTO.getText(), answerDTO.getCorrect(), CONCEPT_ID);


            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            String answerJsonDTO = mapObjectToJson(answerDTO);

            doThrow(
                    new AnswerNotBelongToConceptException(
                            "The answer with id = " + answer.getId() + " doesn't belong to the concept with id = " + concept.getId())
            )
                    .when(answerService).updateOne(concept, answer.getId(), answerDTO);

            mockMvc.perform(put("/concepts/" + concept.getId() + "/answers/" + answer.getId())
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
            final Answer answer = new Answer(ANSWER_ID, "Software answer", true, CONCEPT_ID);
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            mockMvc.perform(delete(BASE_URL + answer.getId()))
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
}
