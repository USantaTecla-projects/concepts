package com.example.backend.api.core.concept;

import com.example.backend.api.core.concept.dto.ConceptDTO;
import com.example.backend.api.core.concept.model.Concept;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConceptController.class)
@AutoConfigureMockMvc
class ConceptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IConceptService conceptService;


    @Test
    @DisplayName("Should get 201 when creating a new Concept")
    void create() throws Exception {
        ConceptDTO conceptDTO = new ConceptDTO("Software", new LinkedList<>());
        String conceptJsonDTO = mapObjectToJson(conceptDTO);

        Concept concept = new Concept(1L, "Software", new LinkedList<>());
        String conceptJson = mapObjectToJson(concept);

        when(conceptService.create(conceptDTO)).thenReturn(concept);

        mockMvc.perform(
                        post("/concepts/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(conceptJsonDTO)
                )
                .andExpect(status().isCreated());
    }

    private String mapObjectToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(object);
    }

}