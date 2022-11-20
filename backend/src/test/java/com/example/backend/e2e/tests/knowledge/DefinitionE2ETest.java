package com.example.backend.e2e.tests.knowledge;

import com.example.backend.e2e.resources.knowledge.concept.dto.ConceptDTO;
import com.example.backend.e2e.resources.knowledge.definition.dto.DefinitionDTO;
import com.example.backend.e2e.util.KnowledgeUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

import static com.example.backend.e2e.util.GetAuthToken.getAuthCookie;
import static org.hamcrest.Matchers.*;

public class DefinitionE2ETest {
    private static String authCookie;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://localhost";
        RestAssured.port = 8443;
        RestAssured.useRelaxedHTTPSValidation();

        authCookie = getAuthCookie();
    }

    @Nested
    @DisplayName("POST")
    class DefinitionPost {

        @Test
        @DisplayName("Should create a Definition if the DTO is correct")
        void createWithCorrectDTO() {
            final Long conceptID = KnowledgeUtils.createConcept(new ConceptDTO("Software"), authCookie);
            final DefinitionDTO definitionDTO = new DefinitionDTO("Software definition", true);

            final Integer definitionID = RestAssured
                    .given()
                            .cookie("AuthToken", authCookie)
                            .contentType("application/json")
                            .pathParam("conceptID", conceptID)
                            .body(definitionDTO)
                    .when()
                            .post("/concepts/{conceptID}/definitions/")
                    .then()
                            .statusCode(HttpStatus.CREATED.value())
                            .contentType(ContentType.JSON)
                            .body("text", res -> equalTo("Software definition"))
                            .body("correct", res -> equalTo(true))
                    .extract()
                        .path("id");

            KnowledgeUtils.deleteDefinition(conceptID, definitionID.longValue(), authCookie);
            KnowledgeUtils.deleteConcept(conceptID, authCookie);
        }

        @Test
        @DisplayName("Should not create a Definition if the DTO is malformed")
        void createWithWrongDTO() {
            final Long conceptID = KnowledgeUtils.createConcept(new ConceptDTO("Software"), authCookie);
            final DefinitionDTO wrongDefinitionDTO = new DefinitionDTO();

            RestAssured
                    .given()
                            .cookie("AuthToken", authCookie)
                            .contentType("application/json")
                            .pathParam("conceptID", conceptID)
                            .body(wrongDefinitionDTO)
                    .when()
                            .post("/concepts/{conceptID}/definitions/")
                    .then()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .contentType(ContentType.JSON)
                            .body("message", res -> equalTo("Field text in definition DTO is mandatory"))
                            .body("httpStatus", res -> equalTo("BAD_REQUEST"));

            KnowledgeUtils.deleteConcept(conceptID, authCookie);
        }

        @Test
        @DisplayName("Should create the Definition in the specified Concept")
        void createDefinitionInTheCorrectConcept() {
            final ConceptDTO conceptDTO = new ConceptDTO("Hardware");
            final DefinitionDTO definitionDTO = new DefinitionDTO("Software definition", true);

            final Long conceptID = KnowledgeUtils.createConcept(conceptDTO, authCookie);
            final Long definitionID = KnowledgeUtils.createDefinition(definitionDTO, conceptID, authCookie);

            List<Map<String, Object>> definitionList = RestAssured
                    .given()
                            .cookie("AuthToken", authCookie)
                            .accept(ContentType.JSON)
                            .pathParam("conceptID", conceptID)
                    .when()
                            .get("/concepts/{conceptID}/definitions/")
                    .then()
                            .statusCode(HttpStatus.OK.value())
                    .extract()
                        .path("");


            Long definitionFounded = definitionList
                    .stream()
                    .map(definitionMap -> Long.valueOf((Integer) definitionMap.get("id")))
                    .filter(id -> id.equals(definitionID))
                    .findAny()
                    .orElse(null);


            assert definitionFounded != null;

            KnowledgeUtils.deleteDefinition(conceptID, definitionID, authCookie);
            KnowledgeUtils.deleteConcept(conceptID, authCookie);
        }

    }

    @Nested
    @DisplayName("GET")
    class DefinitionGet {

        @Test
        @DisplayName("Should find a Definition with the given ID")
        void findOneWhenExists() {
            final Long conceptID = KnowledgeUtils.createConcept(new ConceptDTO("Software"), authCookie);

            final DefinitionDTO definitionDTO = new DefinitionDTO("Software definition", true);
            final Long definitionID = KnowledgeUtils.createDefinition(definitionDTO, conceptID, authCookie);

            RestAssured
                    .given()
                            .accept(ContentType.JSON)
                            .pathParam("conceptID", conceptID)
                            .pathParam("definitionID", definitionID)
                    .when()
                            .get("/concepts/{conceptID}/definitions/{definitionID}")
                    .then()
                            .statusCode(HttpStatus.OK.value())
                            .body("text", res -> equalTo("Software definition"))
                            .body("correct", res -> equalTo(true))
                            .body("conceptID", res -> equalTo(conceptID.intValue()));

            KnowledgeUtils.deleteDefinition(conceptID, definitionID, authCookie);
            KnowledgeUtils.deleteConcept(conceptID, authCookie);
        }

        @Test
        @DisplayName("Should not find a Definition with the given ID")
        void findOneWhenNotExists() {
            final Long conceptID = KnowledgeUtils.createConcept(new ConceptDTO("Software"), authCookie);

            RestAssured
                    .given()
                            .accept(ContentType.JSON)
                            .pathParam("conceptID", conceptID)
                            .pathParam("definitionID", 999)
                    .when()
                            .get("/concepts/{conceptID}/definitions/{definitionID}")
                    .then()
                            .statusCode(HttpStatus.NOT_FOUND.value());

            KnowledgeUtils.deleteConcept(conceptID, authCookie);
        }

        @Test
        @DisplayName("Should find a List of Definitions")
        void findAllWhenExists() {
            final Long conceptID = KnowledgeUtils.createConcept(new ConceptDTO("Software"), authCookie);

            final DefinitionDTO definitionDTO1 = new DefinitionDTO("Software definition", true);
            final DefinitionDTO definitionDTO2 = new DefinitionDTO("Hardware definition", true);
            final DefinitionDTO definitionDTO3 = new DefinitionDTO("Functional Programming definition", false);
            final DefinitionDTO definitionDTO4 = new DefinitionDTO(" definition", false);
            final DefinitionDTO definitionDTO5 = new DefinitionDTO("Haskell definition", true);

            final Long definitionID1 = KnowledgeUtils.createDefinition(definitionDTO1, conceptID, authCookie);
            final Long definitionID2 = KnowledgeUtils.createDefinition(definitionDTO2, conceptID, authCookie);
            final Long definitionID3 = KnowledgeUtils.createDefinition(definitionDTO3, conceptID, authCookie);
            final Long definitionID4 = KnowledgeUtils.createDefinition(definitionDTO4, conceptID, authCookie);
            final Long definitionID5 = KnowledgeUtils.createDefinition(definitionDTO5, conceptID, authCookie);

            RestAssured
                    .given()
                            .accept(ContentType.JSON)
                            .pathParam("conceptID", conceptID)
                    .when()
                            .get("/concepts/{conceptID}/definitions/")
                    .then()
                            .statusCode(HttpStatus.OK.value())
                            .body("size()", greaterThanOrEqualTo(5));

            KnowledgeUtils.deleteDefinition(conceptID, definitionID1, authCookie);
            KnowledgeUtils.deleteDefinition(conceptID, definitionID2, authCookie);
            KnowledgeUtils.deleteDefinition(conceptID, definitionID3, authCookie);
            KnowledgeUtils.deleteDefinition(conceptID, definitionID4, authCookie);
            KnowledgeUtils.deleteDefinition(conceptID, definitionID5, authCookie);
            KnowledgeUtils.deleteConcept(conceptID, authCookie);

        }

        @Test
        @DisplayName("Should find an empty List of Definitions")
        void findAllWhenNotExists() {
            final Long conceptID = KnowledgeUtils.createConcept(new ConceptDTO("Software"), authCookie);

            RestAssured
                    .given()
                            .accept(ContentType.JSON)
                            .pathParam("conceptID", conceptID)
                    .when()
                            .get("/concepts/{conceptID}/definitions/")
                    .then()
                            .statusCode(HttpStatus.OK.value())
                            .body("isEmpty()", is(true));

            KnowledgeUtils.deleteConcept(conceptID, authCookie);
        }

    }

    @Nested
    @DisplayName("PUT")
    class DefinitionPut {

        @Test
        @DisplayName("Should update the Definition")
        void updateWhenExists() {
            final Long conceptID = KnowledgeUtils.createConcept(new ConceptDTO("Software"), authCookie);

            final DefinitionDTO definitionDTO1 = new DefinitionDTO("Software definition", true);
            final DefinitionDTO definitionDTO2 = new DefinitionDTO("Hardware definition", true);


            final Long definitionID = KnowledgeUtils.createDefinition(definitionDTO1, conceptID, authCookie);

            RestAssured
                    .given()
                            .accept(ContentType.JSON)
                            .pathParam("conceptID", conceptID)
                            .pathParam("definitionID", definitionID)
                    .when()
                            .get("/concepts/{conceptID}/definitions/{definitionID}")
                    .then()
                            .statusCode(HttpStatus.OK.value())
                            .body("text", res -> equalTo(definitionDTO1.getText()))
                            .body("correct", res -> equalTo(definitionDTO1.getCorrect()));

            RestAssured
                    .given()
                            .cookie("AuthToken", authCookie)
                            .contentType("application/json")
                            .pathParam("conceptID", conceptID)
                            .pathParam("definitionID", definitionID)
                            .body(definitionDTO2)
                    .when()
                            .put("/concepts/{conceptID}/definitions/{definitionID}")
                    .then()
                            .statusCode(HttpStatus.NO_CONTENT.value());

            RestAssured
                    .given()
                            .accept(ContentType.JSON)
                            .pathParam("conceptID", conceptID)
                            .pathParam("definitionID", definitionID)
                    .when()
                            .get("/concepts/{conceptID}/definitions/{definitionID}")
                    .then()
                            .statusCode(HttpStatus.OK.value())
                            .body("text", res -> equalTo(definitionDTO2.getText()))
                            .body("correct", res -> equalTo(definitionDTO2.getCorrect()));

            KnowledgeUtils.deleteDefinition(conceptID, definitionID, authCookie);
            KnowledgeUtils.deleteConcept(conceptID, authCookie);
        }

        @Test
        @DisplayName("Should throw an Exception")
        void updateWhenNotExists() {
            final DefinitionDTO definitionDTO1 = new DefinitionDTO("Software definition", true);

            RestAssured
                    .given()
                            .cookie("AuthToken", authCookie)
                            .contentType("application/json")
                            .pathParam("conceptID", 9999)
                            .pathParam("definitionID", 9999)
                            .body(definitionDTO1)
                    .when()
                            .put("/concepts/{conceptID}/definitions/{definitionID}")
                    .then()
                            .statusCode(HttpStatus.NOT_FOUND.value());
        }
    }

    @Nested
    @DisplayName("DELETE")
    class DefinitionDelete {
        @Test
        @DisplayName("Should delete the Definition")
        void deleteWhenExits() {
            final Long conceptID = KnowledgeUtils.createConcept(new ConceptDTO("Software"), authCookie);

            final DefinitionDTO definitionDTO = new DefinitionDTO("Software definition", true);
            final Long definitionID = KnowledgeUtils.createDefinition(definitionDTO, conceptID, authCookie);

            RestAssured
                    .given()
                            .cookie("AuthToken", authCookie)
                            .accept(ContentType.JSON)
                            .pathParam("conceptID", conceptID)
                            .pathParam("definitionID", definitionID)
                    .when()
                            .delete("/concepts/{conceptID}/definitions/{definitionID}")
                    .then()
                            .statusCode(HttpStatus.NO_CONTENT.value());

            KnowledgeUtils.deleteConcept(conceptID, authCookie);
        }

        @Test
        @DisplayName("Should throw an Exception")
        void deleteWhenNotExits() {
            final Long conceptID = KnowledgeUtils.createConcept(new ConceptDTO("Software"), authCookie);

            RestAssured
                    .given()
                            .cookie("AuthToken", authCookie)
                            .accept(ContentType.JSON)
                            .pathParam("conceptID", 9999)
                            .pathParam("definitionID", 9999)
                    .when()
                            .delete("/concepts/{conceptID}/definitions/{definitionID}")
                    .then()
                            .statusCode(HttpStatus.NOT_FOUND.value());

            KnowledgeUtils.deleteConcept(conceptID, authCookie);
        }
    }

}
