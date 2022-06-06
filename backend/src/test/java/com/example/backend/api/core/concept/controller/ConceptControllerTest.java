package com.example.backend.api.core.concept.controller;

import com.example.backend.api.core.concept.ConceptController;
import com.example.backend.api.core.concept.IConceptService;
import com.example.backend.api.core.concept.dto.ConceptDTO;
import com.example.backend.api.core.concept.dto.ConceptRestDTO;
import com.example.backend.api.core.concept.exception.model.ConceptDTOBadRequestException;
import com.example.backend.api.core.concept.exception.model.ConceptNotFoundException;
import com.example.backend.api.core.concept.model.Concept;
import com.example.backend.api.core.concept.util.ConceptAssembler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.hamcrest.Matchers;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConceptController.class)
class ConceptControllerTest {

    public static final String BASE_URL = "/concepts/";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConceptAssembler conceptAssembler;

    @MockBean
    private IConceptService conceptService;

    private final ConceptDTO conceptDTO1 = new ConceptDTO("Software");
    private final ConceptDTO conceptDTO2 = new ConceptDTO("Hardware");
    private final ConceptDTO conceptDTO3 = new ConceptDTO("Functional Programming");

    private final ConceptDTO wrongConceptDTO = new ConceptDTO();

    private final Concept concept1 = new Concept(1L, "Software", new LinkedList<>());
    private final Concept concept2 = new Concept(2L, "Hardware", new LinkedList<>());
    private final Concept concept3 = new Concept(3L, "Functional Programming", new LinkedList<>());

    private final Page<Concept> conceptPage = new PageImpl(List.of(concept1, concept2, concept3));

    @BeforeEach
    void setUpConceptServiceMocks() {

        lenient().when(conceptService.create(conceptDTO1)).thenReturn(concept1);
        lenient().when(conceptService.create(conceptDTO2)).thenReturn(concept2);
        lenient().when(conceptService.create(conceptDTO3)).thenReturn(concept3);

        lenient().when(conceptService.findOne(1L)).thenReturn(concept1);
        lenient().when(conceptService.findOne(2L)).thenReturn(concept2);
        lenient().when(conceptService.findOne(3L)).thenReturn(concept3);

        lenient().when(conceptService.findAll(0)).thenReturn(conceptPage);

    }

    @BeforeEach
    void setUpConceptAssemblerMocks() {
        lenient()
                .when(conceptAssembler.toModel(concept1)).thenReturn(
                        EntityModel.of(new ConceptRestDTO(concept1.getId(), concept1.getText()),
                                linkTo(methodOn(ConceptController.class).findOne(concept1.getId())).withSelfRel(),
                                linkTo(methodOn(ConceptController.class).findAll(null)).withRel("concepts")
                        )
                );

        lenient()
                .when(conceptAssembler.toCollectionModel(conceptPage.getContent())).thenReturn(
                        CollectionModel.of(
                                conceptPage.getContent()
                                        .stream()
                                        .map(concept -> EntityModel.of(new ConceptRestDTO(concept.getId(), concept.getText())))
                                        .toList()
                        )
                );

        lenient()
                .when(conceptAssembler.toCollectionPageModel(
                        conceptPage.getContent(),
                        conceptPage.getTotalPages(),
                        conceptPage.getNumber()
                ))
                .thenCallRealMethod();

    }


    @Nested
    @DisplayName("POST")
    class ConceptPost {
        @Test
        @DisplayName("(Create) Should get 201 when creating a Concept")
        void createWithCorrectDTO() throws Exception {
            String conceptJsonDTO = mapObjectToJson(conceptDTO1);

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(conceptJsonDTO))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.text", Matchers.is("Software")))
                    .andExpect(jsonPath("$.id", Matchers.is(1)))
                    .andExpect(jsonPath("$._links.self").exists())
                    .andExpect(jsonPath("$._links.concepts").exists());
        }

        @Test
        @DisplayName("Create) Should get 400 when creating a wrong Concept")
        void createWithWrongDTO() throws Exception {
            lenient()
                    .when(conceptService.create(wrongConceptDTO))
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
            String conceptJsonDTO = mapObjectToJson(conceptDTO1);

            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(conceptJsonDTO));

            mockMvc.perform(get(BASE_URL + concept1.getId()))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("(FindOne) Should get 404 when the given id doesn't exists")
        void findOneWhenNotExists() throws Exception {
            lenient()
                    .when(conceptService.findOne(99L))
                    .thenThrow(new ConceptNotFoundException("The concept with id = " + 99L + " has not been found"));

            mockMvc.perform(get(BASE_URL + "99"))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("(FindAll) Should get 200 if there are entities")
        void findAllWithEntitiesInDatabase() throws Exception {
            createConcept(conceptDTO1);
            createConcept(conceptDTO2);
            createConcept(conceptDTO3);

            mockMvc.perform(get(BASE_URL + "?page=0"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$._embedded").exists())
                    .andExpect(jsonPath("$._embedded.conceptRestDTOList.size()", Matchers.is(3)))
                    .andExpect(jsonPath("$._links.first").exists())
                    .andExpect(jsonPath("$._links.prev").exists())
                    .andExpect(jsonPath("$._links.self").exists())
                    .andExpect(jsonPath("$._links.next").exists())
                    .andExpect(jsonPath("$._links.last").exists());
        }

        @Test
        @DisplayName("(FindAll) Should get 404 if there are no entities")
        void findAllWithNoEntitiesInDatabase() throws Exception {

            lenient()
                    .when(conceptService.findAll(1))
                    .thenThrow(new ConceptNotFoundException("The requested page doesn't exists"));

            mockMvc.perform(get(BASE_URL + "?page=1"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("PUT")
    class ConceptPut {

        @Test
        @DisplayName("(UpdateOne) Should get 204 updating the Concept")
        void updateExistingConcept() throws Exception {
            createConcept(conceptDTO1);

            String conceptJsonDTO = mapObjectToJson(conceptDTO1);

            mockMvc.perform(put(BASE_URL + "1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(conceptJsonDTO))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("(UpdateOne) Should get 404 deleting the Concept")
        void updateNonExistingConcept() throws Exception {
            createConcept(conceptDTO1);

            String conceptJsonDTO = mapObjectToJson(conceptDTO1);

            doThrow(new ConceptNotFoundException("The concept with id = " + 99L + " has not been found"))
                    .when(conceptService).updateOne(99L, conceptDTO1);

            mockMvc.perform(put(BASE_URL + "99")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(conceptJsonDTO))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("DELETE")
    class ConceptDelete {
        @Test
        @DisplayName("(RemoveOne) Should get 204 deleting the Concept")
        void deleteExistingConcept() throws Exception {

            mockMvc.perform(delete(BASE_URL + "1"))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("(RemoveOne) Should get 404 deleting the Concept")
        void deleteNonExistingConcept() throws Exception {
            doThrow(new ConceptNotFoundException("The concept with id = " + 99L + " has not been found"))
                    .when(conceptService).removeOne(99L);

            mockMvc.perform(delete(BASE_URL + "99"))
                    .andExpect(status().isNotFound());
        }
    }


    /**
     * Auxiliary method to create a Concept.
     *
     * @param conceptDTO The Concept with the info to be created.
     * @throws Exception If something goes wrong, Exception is raised.
     */
    private void createConcept(ConceptDTO conceptDTO) throws Exception {
        String conceptJsonDTO = mapObjectToJson(conceptDTO);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(conceptJsonDTO));
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