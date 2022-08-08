package com.example.backend.api.core.justification;


import com.example.backend.api.resources.auth.configuration.AuthConfiguration;
import com.example.backend.api.resources.auth.jwt.components.JwtRequestFilter;
import com.example.backend.api.resources.auth.jwt.components.JwtTokenProvider;
import com.example.backend.api.resources.auth.util.UserDetailsFinder;
import com.example.backend.api.resources.knowledge.answer.AnswerService;
import com.example.backend.api.resources.knowledge.answer.model.Answer;
import com.example.backend.api.resources.knowledge.concept.ConceptService;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import com.example.backend.api.resources.knowledge.justification.JustificationController;
import com.example.backend.api.resources.knowledge.justification.JustificationService;
import com.example.backend.api.resources.knowledge.justification.dto.JustificationDTO;
import com.example.backend.api.resources.knowledge.justification.exception.model.JustificationDTOBadRequestException;
import com.example.backend.api.resources.knowledge.justification.exception.model.JustificationNotBelongToAnswerException;
import com.example.backend.api.resources.knowledge.justification.exception.model.JustificationNotFoundException;
import com.example.backend.api.resources.knowledge.justification.model.Justification;
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

import java.util.LinkedList;
import java.util.List;

import static com.example.backend.util.MapObjectToJson.mapObjectToJson;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JustificationController.class)
@Import({
        AuthConfiguration.class,
        UserDetailsFinder.class,
        JwtRequestFilter.class,
        JwtTokenProvider.class,
})
@WithMockUser(username = "teacher", roles = "TEACHER")
public class JustificationControllerTest {

    public final static long CONCEPT_ID = 1L;
    public final static long ANSWER_ID = 2L;
    public final static long JUSTIFICATION_ID = 3L;

    public static final String BASE_URL = "/concepts/" + CONCEPT_ID + "/answers/" + ANSWER_ID + "/justifications/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnswerService answerService;

    @MockBean
    private ConceptService conceptService;

    @MockBean
    private JustificationService justificationsService;

    @MockBean
    private UserRepository userRepository;

    @Nested
    @DisplayName("POST")
    class JustificationPost {

        @Test
        @DisplayName("(Create) Should get 201 if the DTO is correct")
        void createWithCorrectDTO() throws Exception {
            final JustificationDTO justificationDTO = new JustificationDTO("Software Justification", true, null);
            final Justification justification = new Justification(
                    JUSTIFICATION_ID,
                    justificationDTO.getText(),
                    justificationDTO.getIsCorrect(),
                    justificationDTO.getError(),
                    CONCEPT_ID,
                    ANSWER_ID);
            final Answer answer = new Answer(
                    ANSWER_ID,
                    "Software answer",
                    true,
                    CONCEPT_ID,
                    new LinkedList<>(List.of(justification)));
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.findOne(concept, answer.getId()))
                    .thenReturn(answer);

            when(justificationsService.create(concept.getId(), answer, justificationDTO))
                    .thenReturn(justification);

            final String justificationJsonDTO = mapObjectToJson(justificationDTO);

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(justificationJsonDTO))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("text", Matchers.is(justification.getText())))
                    .andExpect(jsonPath("id").exists())
                    .andExpect(jsonPath("correct", Matchers.is(justification.getCorrect())));


        }

        @Test
        @DisplayName("Create) Should get 400 if the DTO is malformed")
        void createWithWrongDTO() {
            final JustificationDTO justificationDTO = new JustificationDTO();
            final Justification justification = new Justification(JUSTIFICATION_ID, "Software Justification", true, null, CONCEPT_ID, ANSWER_ID);
            final Answer answer = new Answer(ANSWER_ID, "Software answer", true, CONCEPT_ID, new LinkedList<>(List.of(justification)));
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.findOne(concept, answer.getId()))
                    .thenReturn(answer);

            when(justificationsService.create(concept.getId(), answer, justificationDTO))
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

            mockMvc.perform(get(BASE_URL + justification.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("id").exists())
                    .andExpect(jsonPath("text", Matchers.is(justification.getText())));

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


            mockMvc.perform(get(BASE_URL))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray());

        }

        @Test
        @DisplayName("(FindAll) Should get 404 if the answers justifications list is empty")
        void findAllWhenDataNotExists() {
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
            final JustificationDTO justificationDTO = new JustificationDTO("Software Justification", true, null);
            final Justification justification = new Justification(JUSTIFICATION_ID, "Software Justification", true, null, CONCEPT_ID, ANSWER_ID);
            final Answer answer = new Answer(ANSWER_ID, "Software answer", true, CONCEPT_ID, new LinkedList<>(List.of(justification)));
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.findOne(concept, answer.getId()))
                    .thenReturn(answer);

            String justificationJsonDTO = mapObjectToJson(justificationDTO);

            mockMvc.perform(put("/concepts/" + concept.getId() + "/answers/" + answer.getId() + "/justifications/" + justification.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(justificationJsonDTO))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("(UpdateOne) Should get 404 if the Answer is not in the database")
        void updateWhenNotExists() throws Exception {
            final JustificationDTO justificationDTO = new JustificationDTO("Software Justification", true, null);
            final Answer answer = new Answer(ANSWER_ID, "Software answer", true, CONCEPT_ID, new LinkedList<>(List.of()));
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));
            final long wrongJustificationId = 99L;

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.findOne(concept, answer.getId()))
                    .thenReturn(answer);

            doThrow(new JustificationNotFoundException("The justification with id = " + wrongJustificationId + " has not been found"))
                    .when(justificationsService).updateOne(answer, wrongJustificationId, justificationDTO);

            String justificationJsonDTO = mapObjectToJson(justificationDTO);

            mockMvc.perform(put("/concepts/" + concept.getId() + "/answers/" + answer.getId() + "/justifications/" + wrongJustificationId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(justificationJsonDTO))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("(UpdateOne) Should get 404 if the answer does not belong to the concept answers list")
        void updateWhenNotBelongsToConcept() throws Exception {
            final JustificationDTO justificationDTO = new JustificationDTO("Software Justification", true, null);
            final Justification justification = new Justification(JUSTIFICATION_ID, "Software Justification", true, null, CONCEPT_ID, ANSWER_ID);
            final Answer answer = new Answer(ANSWER_ID, "Software answer", true, CONCEPT_ID, new LinkedList<>(List.of()));
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(answer)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(answerService.findOne(concept, answer.getId()))
                    .thenReturn(answer);

            doThrow(new JustificationNotBelongToAnswerException("The justification with id = " + justification.getId() + " doesn't belong to the answer with id = " + answer.getId()))
                    .when(justificationsService).updateOne(answer, justification.getId(), justificationDTO);

            String justificationJsonDTO = mapObjectToJson(justificationDTO);

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
}
