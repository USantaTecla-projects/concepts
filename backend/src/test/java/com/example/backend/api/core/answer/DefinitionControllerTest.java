package com.example.backend.api.core.answer;

import com.example.backend.api.resources.auth.configuration.AuthConfiguration;
import com.example.backend.api.resources.auth.jwt.components.JwtRequestFilter;
import com.example.backend.api.resources.auth.jwt.components.JwtTokenProvider;
import com.example.backend.api.resources.auth.util.UserDetailsFinder;
import com.example.backend.api.resources.knowledge.definition.DefinitionController;
import com.example.backend.api.resources.knowledge.definition.DefinitionService;
import com.example.backend.api.resources.knowledge.definition.dto.DefinitionDTO;
import com.example.backend.api.resources.knowledge.definition.exception.specific.DefinitionDTOBadRequestException;
import com.example.backend.api.resources.knowledge.definition.exception.specific.DefinitionNotBelongToConceptException;
import com.example.backend.api.resources.knowledge.definition.exception.specific.DefinitionNotFoundException;
import com.example.backend.api.resources.knowledge.definition.model.Definition;
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

@WebMvcTest(DefinitionController.class)
@Import({
        AuthConfiguration.class,
        UserDetailsFinder.class,
        JwtRequestFilter.class,
        JwtTokenProvider.class
})
@WithMockUser(username = "teacher", roles = "TEACHER")
public class DefinitionControllerTest {

    public final static long CONCEPT_ID = 1L;
    public final static long ANSWER_ID = 2L;
    public final String BASE_URL = "/concepts/" + CONCEPT_ID + "/answers/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DefinitionService definitionService;

    @MockBean
    private ConceptService conceptService;

    @MockBean
    private UserRepository userRepository;


    @Nested
    @DisplayName("POST")
    class DefinitionPost {
        @Test
        @DisplayName("(Create) Should get 201 if the DTO is correct")
        void createWithCorrectDTO() throws Exception {
            final DefinitionDTO definitionDTO = new DefinitionDTO("Software answer", true);
            final Definition definition = new Definition(ANSWER_ID, definitionDTO.getText(), definitionDTO.getCorrect(), CONCEPT_ID, Collections.emptyList());
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(definition)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(definitionService.create(concept, definitionDTO))
                    .thenReturn(definition);

            final String answerJsonDTO = mapObjectToJson(definitionDTO);

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(answerJsonDTO))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("text", Matchers.is(definition.getText())))
                    .andExpect(jsonPath("id").exists())
                    .andExpect(jsonPath("correct", Matchers.is(definition.getCorrect())));
        }

        @Test
        @DisplayName("Create) Should get 400 if the DTO is malformed")
        void createWithWrongDTO() throws Exception {
            final DefinitionDTO wrongConceptDTO = new DefinitionDTO();
            final Definition definition = new Definition(ANSWER_ID, "Software answer", true, CONCEPT_ID);
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(definition)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(definitionService.create(concept, wrongConceptDTO))
                    .thenThrow(new DefinitionDTOBadRequestException("Field text in Answer DTO is mandatory"));

            final String conceptJsonDTO = mapObjectToJson(wrongConceptDTO);

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(conceptJsonDTO))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("GET")
    class DefinitionGet {
        @Test
        @DisplayName("(FindOne) Should get 200 if the answer exists on the concept answers list")
        void findOneWhenExists() throws Exception {
            final Definition definition = new Definition(ANSWER_ID, "Software answer", true, CONCEPT_ID);
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(definition)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(definitionService.findOne(concept, definition.getId()))
                    .thenReturn(definition);


            mockMvc.perform(get(BASE_URL + definition.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("id").exists())
                    .andExpect(jsonPath("text", Matchers.is(definition.getText())));
        }

        @Test
        @DisplayName("(FindOne) Should get 404 if the given id does not match in the database")
        void findOneWhenNotExists() throws Exception {
            final Definition definition = new Definition(ANSWER_ID, "Software answer", true, CONCEPT_ID);
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(definition)));
            final long wrongAnswerId = 99L;

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(definitionService.findOne(concept, wrongAnswerId))
                    .thenThrow(new DefinitionNotFoundException("The answer with id = " + definition.getId() + " has not been found"));

            mockMvc.perform(get(BASE_URL + wrongAnswerId))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("(FindOne) Should get 404 if the given id does not exists on the concept answers list")
        void findOneWhenNotBelongsToConcept() throws Exception {
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>());
            final Definition definition = new Definition(ANSWER_ID, "Software answer", true, 3L);

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(definitionService.findOne(concept, definition.getId()))
                    .thenThrow(new DefinitionNotBelongToConceptException(
                            "The answer with id = " + definition.getId() + " doesn't belong to the concept with id = " + concept.getId()
                    ));


            mockMvc.perform(get(BASE_URL + definition.getId()))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("(FindAll) Should get 200 if the concept have answers in its answers list")
        void findAllWhenDataExists() throws Exception {
            final Definition definition1 = new Definition(ANSWER_ID, "Software answer", true, CONCEPT_ID);
            final Definition definition2 = new Definition(3L, "Hardware answer", true, CONCEPT_ID);
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(definition1, definition2)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            when(definitionService.findAll(concept))
                    .thenReturn(concept.getDefinitionList());


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

            when(definitionService.findAll(concept))
                    .thenThrow(new DefinitionNotFoundException("The concept with id = " + concept.getId() + " has no answers"));

            mockMvc.perform(get("/concepts/" + concept.getId() + "/answers/" + "?page=1"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("PUT")
    class DefinitionPut {
        @Test
        @DisplayName("(UpdateOne) Should get 204 if the justification is in the concept answers list")
        void updateWhenExists() throws Exception {
            final DefinitionDTO definitionDTO = new DefinitionDTO("Software answer", true);
            final Definition definition = new Definition(ANSWER_ID, definitionDTO.getText(), definitionDTO.getCorrect(), CONCEPT_ID);
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(definition)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            String answerJsonDTO = mapObjectToJson(definitionDTO);

            mockMvc.perform(put("/concepts/" + concept.getId() + "/answers/" + definition.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(answerJsonDTO))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("(UpdateOne) Should get 404 if the Answer is not in the database")
        void updateWhenNotExists() throws Exception {
            final DefinitionDTO definitionDTO = new DefinitionDTO("Software answer", true);
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>());
            final long wrongAnswerId = 99L;

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            doThrow(new DefinitionNotFoundException("The answer with id = " + wrongAnswerId + " has not been found"))
                    .when(definitionService).updateOne(concept, wrongAnswerId, definitionDTO);

            String answerJsonDTO = mapObjectToJson(definitionDTO);

            mockMvc.perform(put("/concepts/" + concept.getId() + "/answers/" + wrongAnswerId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(answerJsonDTO))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("(UpdateOne) Should get 404 if the answer does not belong to the concept answers list")
        void updateWhenNotBelongsToConcept() throws Exception {
            final DefinitionDTO definitionDTO = new DefinitionDTO("Software answer", true);
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>());
            final Definition definition = new Definition(ANSWER_ID, definitionDTO.getText(), definitionDTO.getCorrect(), CONCEPT_ID);


            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            String answerJsonDTO = mapObjectToJson(definitionDTO);

            doThrow(
                    new DefinitionNotBelongToConceptException(
                            "The answer with id = " + definition.getId() + " doesn't belong to the concept with id = " + concept.getId())
            )
                    .when(definitionService).updateOne(concept, definition.getId(), definitionDTO);

            mockMvc.perform(put("/concepts/" + concept.getId() + "/answers/" + definition.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(answerJsonDTO))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("DELETE")
    class DefinitionDelete {
        @Test
        @DisplayName("(RemoveOne) Should get 204 if the answer is in the concept answers list")
        void deleteWhenExists() throws Exception {
            final Definition definition = new Definition(ANSWER_ID, "Software answer", true, CONCEPT_ID);
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>(List.of(definition)));

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            mockMvc.perform(delete(BASE_URL + definition.getId()))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("(RemoveOne) Should get 404 if the answer is not in the database")
        void deleteWhenNotExists() throws Exception {
            final Concept concept = new Concept(CONCEPT_ID, "Software", new LinkedList<>());
            final long wrongAnswerId = 99L;

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            doThrow(new DefinitionNotFoundException("The answer with id = " + wrongAnswerId + " has not been found"))
                    .when(definitionService).removeOne(concept, wrongAnswerId);

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
                    new DefinitionNotBelongToConceptException(
                            "The answer with id = " + wrongAnswerId + " doesn't belong to the concept with id = " + concept.getId())
            )
                    .when(definitionService).removeOne(concept, wrongAnswerId);

            mockMvc.perform(delete(BASE_URL + wrongAnswerId))
                    .andExpect(status().isNotFound());
        }
    }
}
