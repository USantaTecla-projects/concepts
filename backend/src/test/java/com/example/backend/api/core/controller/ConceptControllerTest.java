package com.example.backend.api.core.controller;

import com.example.backend.api.core.concept.ConceptController;
import com.example.backend.api.core.concept.ConceptService;
import com.example.backend.api.core.concept.dto.ConceptDTO;
import com.example.backend.api.core.concept.exception.model.ConceptDTOBadRequestException;
import com.example.backend.api.core.concept.exception.model.ConceptNotFoundException;
import com.example.backend.api.core.concept.model.Concept;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConceptController.class)
class ConceptControllerTest {

    public static final String BASE_URL = "/concepts/";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConceptService conceptService;

    @Nested
    @DisplayName("POST")
    class ConceptPost {
        @Test
        @DisplayName("(Create) Should get 201 if the concept DTO is correct")
        void createWithCorrectDTO() throws Exception {
            final ConceptDTO conceptDTO = new ConceptDTO("Software");
            final Concept concept = new Concept(1L, "Software", new LinkedList<>());

            when(conceptService.create(conceptDTO))
                    .thenReturn(concept);

            String conceptJsonDTO = mapObjectToJson(conceptDTO);

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(conceptJsonDTO))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.text", Matchers.is("Software")))
                    .andExpect(jsonPath("$.id", Matchers.is(1)));
        }

        @Test
        @DisplayName("Create) Should get 400 the concept DTO is malformed")
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
        @DisplayName("(FindOne) Should get 200 when finding a Concept by a given id if exists")
        void findOneWhenExists() throws Exception {
            final ConceptDTO conceptDTO = new ConceptDTO("Software");
            final Concept concept = new Concept(1L, "Software", new LinkedList<>());

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
        @DisplayName("(FindOne) Should get 404 when the given id doesn't exists")
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
            final Concept concept1 = new Concept(1L, "Software", new LinkedList<>());
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

        @Test
        @DisplayName("(FindAll) Should get 404 if there are no entities")
        void findAllWhenDataNotExists() throws Exception {
            final int wrongPageNumber = 99;

            when(conceptService.findAll(wrongPageNumber))
                    .thenThrow(new ConceptNotFoundException("The requested page doesn't exists"));

            mockMvc.perform(get(BASE_URL + "?page=" + wrongPageNumber))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("PUT")
    class ConceptPut {

        @Test
        @DisplayName("(UpdateOne) Should get 204 updating the Concept")
        void updateWhenExists() throws Exception {
            final ConceptDTO conceptDTO = new ConceptDTO("Software");
            final Concept concept = new Concept(1L, "Software", new LinkedList<>());

            String conceptJsonDTO = mapObjectToJson(conceptDTO);

            mockMvc.perform(put(BASE_URL + concept.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(conceptJsonDTO))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("(UpdateOne) Should get 404 deleting the Concept")
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
            final long conceptId = 1L;

            mockMvc.perform(delete(BASE_URL + conceptId))
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