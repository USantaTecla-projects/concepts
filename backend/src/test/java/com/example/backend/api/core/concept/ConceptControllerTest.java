package com.example.backend.api.core.concept;

import com.example.backend.api.resources.auth.configuration.AuthConfiguration;
import com.example.backend.api.resources.auth.jwt.components.JwtRequestFilter;
import com.example.backend.api.resources.auth.jwt.components.JwtTokenProvider;
import com.example.backend.api.resources.auth.util.UserDetailsFinder;
import com.example.backend.api.resources.core.concept.ConceptController;
import com.example.backend.api.resources.core.concept.ConceptService;
import com.example.backend.api.resources.core.concept.dto.ConceptDTO;
import com.example.backend.api.resources.core.concept.exception.model.ConceptDTOBadRequestException;
import com.example.backend.api.resources.core.concept.exception.model.ConceptNotFoundException;
import com.example.backend.api.resources.core.concept.model.Concept;
import com.example.backend.api.resources.user.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.List;

import static com.example.backend.util.MapObjectToJson.mapObjectToJson;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConceptController.class)
@Import({
        AuthConfiguration.class,
        UserDetailsFinder.class,
        JwtRequestFilter.class,
        JwtTokenProvider.class
})
@WithMockUser(username = "teacher", roles = "TEACHER")
class ConceptControllerTest {

    public final static long CONCEPT_ID = 1L;
    public static final String BASE_URL = "/concepts/";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConceptService conceptService;

    @MockBean
    private UserRepository userRepository;

    @Nested
    @DisplayName("POST")
    class ConceptPost {
        @Test
        @DisplayName("(Create) Should get 201 if the concept DTO is correct")
        void createWithCorrectDTO() throws Exception {
            final ConceptDTO conceptDTO = new ConceptDTO("Software");
            final Concept concept = new Concept(CONCEPT_ID, conceptDTO.getText(), new LinkedList<>());

            when(conceptService.create(conceptDTO))
                    .thenReturn(concept);

            String conceptJsonDTO = mapObjectToJson(conceptDTO);

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(conceptJsonDTO))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.text", Matchers.is(concept.getText())))
                    .andExpect(jsonPath("$.id", Matchers.is(concept.getId().intValue())));
        }

        @Test
        @DisplayName("Create) Should get 400 if the concept DTO is malformed")
        void createWithWrongDTO() throws Exception {
            final ConceptDTO wrongConceptDTO = new ConceptDTO();

            when(conceptService.create(wrongConceptDTO))
                    .thenThrow(new ConceptDTOBadRequestException("Field text in DTO is mandatory"));

            String conceptJsonDTO = mapObjectToJson(wrongConceptDTO);

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(conceptJsonDTO))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("GET")
    class ConceptGet {
        @Test
        @DisplayName("(FindOne) Should get 200 if if the concept by a given id exists")
        void findOneWhenExists() throws Exception {
            final ConceptDTO conceptDTO = new ConceptDTO("Software");
            final Concept concept = new Concept(CONCEPT_ID, conceptDTO.getText(), new LinkedList<>());

            when(conceptService.findOne(concept.getId()))
                    .thenReturn(concept);

            String conceptJsonDTO = mapObjectToJson(conceptDTO);

            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(conceptJsonDTO));

            mockMvc.perform(get(BASE_URL + concept.getId()))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("(FindOne) Should get 404 if the given id does not exist")
        void findOneWhenNotExists() throws Exception {
            final long wrongConceptId = 99L;

            when(conceptService.findOne(wrongConceptId))
                    .thenThrow(new ConceptNotFoundException("The concept with id = " + wrongConceptId + " has not been found"));

            mockMvc.perform(get(BASE_URL + wrongConceptId))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("(FindAll) Should get 200 if there are entities")
        void findAllWhenDataExists() throws Exception {
            final Concept concept1 = new Concept(CONCEPT_ID, "Software", new LinkedList<>());
            final Concept concept2 = new Concept(3L, "Hardware", new LinkedList<>());
            final Concept concept3 = new Concept(5L, "Functional Programming", new LinkedList<>());
            Page<Concept> conceptPage = new PageImpl<>(List.of(concept1, concept2, concept3));

            when(conceptService.findAll(conceptPage.getNumber()))
                    .thenReturn(conceptPage);


            mockMvc.perform(get(BASE_URL + "?page=" + conceptPage.getNumber()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").exists())
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.pageable").exists())
                    .andExpect(jsonPath("$.totalPages").isNumber());
        }
    }

    @Nested
    @DisplayName("PUT")
    class ConceptPut {

        @Test
        @DisplayName("(UpdateOne) Should get 204 if the concept is updated")
        void updateWhenExists() throws Exception {
            final ConceptDTO conceptDTO = new ConceptDTO("Software");
            final Concept concept = new Concept(CONCEPT_ID, "Hardware", new LinkedList<>());

            String conceptJsonDTO = mapObjectToJson(conceptDTO);

            mockMvc.perform(put(BASE_URL + concept.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(conceptJsonDTO))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("(UpdateOne) Should get 404 if the concept does not exist")
        void updateWhenNotExists() throws Exception {
            final ConceptDTO conceptDTO = new ConceptDTO("Software");
            final long wrongConceptId = 99L;

            doThrow(new ConceptNotFoundException("The concept with id = " + wrongConceptId + " has not been found"))
                    .when(conceptService).updateOne(wrongConceptId, conceptDTO);

            String conceptJsonDTO = mapObjectToJson(conceptDTO);

            mockMvc.perform(put(BASE_URL + wrongConceptId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(conceptJsonDTO))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("DELETE")
    class ConceptDelete {
        @Test
        @DisplayName("(RemoveOne) Should get 204 if the concept exists")
        void deleteWhenExists() throws Exception {

            mockMvc.perform(delete(BASE_URL + CONCEPT_ID))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("(RemoveOne) Should get 404 if the concept does not exists")
        void deleteWhenNotExists() throws Exception {
            final long wrongConceptId = 99L;

            doThrow(new ConceptNotFoundException("The concept with id = " + wrongConceptId + " has not been found"))
                    .when(conceptService).removeOne(wrongConceptId);

            mockMvc.perform(delete(BASE_URL + wrongConceptId))
                    .andExpect(status().isNotFound());
        }
    }
}