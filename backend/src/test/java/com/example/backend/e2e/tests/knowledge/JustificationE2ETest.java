package com.example.backend.e2e.tests.knowledge;

import com.example.backend.e2e.resources.knowledge.concept.dto.ConceptDTO;
import com.example.backend.e2e.resources.knowledge.definition.dto.DefinitionDTO;
import com.example.backend.e2e.resources.knowledge.justification.dto.JustificationDTO;
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
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class JustificationE2ETest {

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
    class JustificationPost {

        @Test
        @DisplayName("Should create a Justification if the DTO is correct")
        void createWithCorrectDTO() {
            final ConceptDTO conceptDTO = new ConceptDTO("Hardware");
            final DefinitionDTO definitionDTO = new DefinitionDTO("Software definition", true);
            final JustificationDTO justificationDTO = new JustificationDTO("Software justification", true, null);

            final Long conceptID = KnowledgeUtils.createConcept(conceptDTO, authCookie);
            final Long definitionID = KnowledgeUtils.createDefinition(definitionDTO, conceptID, authCookie);

            final Integer justificationID = RestAssured
                    .given()
                            .cookie("AuthToken", authCookie)
                            .contentType("application/json")
                            .pathParam("conceptID", conceptID)
                            .pathParam("definitionID", definitionID)
                            .body(justificationDTO)
                    .when()
                            .post("/concepts/{conceptID}/definitions/{definitionID}/justifications/")
                    .then()
                            .statusCode(HttpStatus.CREATED.value())
                            .contentType(ContentType.JSON)
                            .body("text", res -> equalTo("Software justification"))
                            .body("correct", res -> equalTo(true))
                     .extract()
                        .path("id");

            KnowledgeUtils.deleteJustification(conceptID, definitionID, justificationID.longValue(), authCookie);
            KnowledgeUtils.deleteDefinition(conceptID, definitionID, authCookie);
            KnowledgeUtils.deleteConcept(conceptID, authCookie);
        }

        @Test
        @DisplayName("Should not create a Justification if the DTO is malformed")
        void createWithWrongDTO() {
            final ConceptDTO conceptDTO = new ConceptDTO("Hardware");
            final DefinitionDTO definitionDTO = new DefinitionDTO("Software definition", true);
            final JustificationDTO wrongJustificationDTO = new JustificationDTO();

            final Long conceptID = KnowledgeUtils.createConcept(conceptDTO, authCookie);
            final Long definitionID = KnowledgeUtils.createDefinition(definitionDTO, conceptID, authCookie);

            RestAssured
                    .given()
                            .cookie("AuthToken", authCookie)
                            .contentType("application/json")
                            .pathParam("conceptID", conceptID)
                            .pathParam("definitionID", definitionID)
                            .body(wrongJustificationDTO)
                    .when()
                            .post("/concepts/{conceptID}/definitions/{definitionID}/justifications/")
                    .then()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .contentType(ContentType.JSON)
                            .body("message", res -> equalTo("Field text in Justification DTO is mandatory"))
                            .body("httpStatus", res -> equalTo("BAD_REQUEST"));

            KnowledgeUtils.deleteDefinition(conceptID, definitionID, authCookie);
            KnowledgeUtils.deleteConcept(conceptID, authCookie);
        }

        @Test
        @DisplayName("Should create the Justification in the specified Definition")
        void createDefinitionInTheCorrectConcept() {
            final ConceptDTO conceptDTO = new ConceptDTO("Software");
            final DefinitionDTO definitionDTO = new DefinitionDTO("Software definition", true);
            final JustificationDTO justificationDTO = new JustificationDTO("Software justification", true, null);

            final Long conceptID = KnowledgeUtils.createConcept(conceptDTO, authCookie);
            final Long definitionID = KnowledgeUtils.createDefinition(definitionDTO, conceptID, authCookie);
            final Long justificationID = KnowledgeUtils.createJustification(justificationDTO, conceptID, definitionID, authCookie);

            List<Map<String, Object>> justificationList = RestAssured
                    .given()
                            .accept(ContentType.JSON)
                            .pathParams("conceptID", conceptID)
                            .pathParam("definitionID", definitionID)
                    .when()
                            .get("/concepts/{conceptID}/definitions/{definitionID}/justifications/")
                    .then()
                            .statusCode(HttpStatus.OK.value())
                    .extract()
                            .path("");

            Long justificationFound = justificationList
                    .stream()
                    .map(justificationMap -> Long.valueOf((Integer) justificationMap.get("id")))
                    .filter(id -> id.equals(justificationID))
                    .findAny()
                    .orElse(null);

            assert justificationFound != null;

            KnowledgeUtils.deleteJustification(conceptID, definitionID, justificationID, authCookie);
            KnowledgeUtils.deleteDefinition(conceptID, definitionID, authCookie);
            KnowledgeUtils.deleteConcept(conceptID, authCookie);
        }
    }

    @Nested
    @DisplayName("GET")
    class JustificationGet {

        @Test
        @DisplayName("Should find a Justification with the given ID")
        void findOneWhenExists() {
            final ConceptDTO conceptDTO = new ConceptDTO("Software");
            final DefinitionDTO definitionDTO = new DefinitionDTO("Software definition", true);
            final JustificationDTO justificationDTO = new JustificationDTO("Software justification", true, null);

            final Long conceptID = KnowledgeUtils.createConcept(conceptDTO, authCookie);
            final Long definitionID = KnowledgeUtils.createDefinition(definitionDTO, conceptID, authCookie);
            final Long justificationID = KnowledgeUtils.createJustification(justificationDTO, conceptID, definitionID, authCookie);

            given()
                            .accept(ContentType.JSON)
                            .pathParam("conceptID", conceptID)
                            .pathParam("definitionID", definitionID)
                            .pathParam("justificationID", justificationID)
                    .when()
                            .get("/concepts/{conceptID}/definitions/{definitionID}/justifications/{justificationID}")
                    .then()
                            .statusCode(HttpStatus.OK.value())
                            .body("text", res -> equalTo("Software justification"))
                            .body("correct", res -> equalTo(true))
                            .body("conceptID", res -> equalTo(conceptID.intValue()));

            KnowledgeUtils.deleteJustification(conceptID, definitionID, justificationID, authCookie);
            KnowledgeUtils.deleteDefinition(conceptID, definitionID, authCookie);
            KnowledgeUtils.deleteConcept(conceptID, authCookie);
        }

        @Test
        @DisplayName("Should not find a Definition with the given ID")
        void findOneWhenNotExists() {
            final ConceptDTO conceptDTO = new ConceptDTO("Software");
            final DefinitionDTO definitionDTO = new DefinitionDTO("Software definition", true);

            final Long conceptID = KnowledgeUtils.createConcept(conceptDTO, authCookie);
            final Long definitionID = KnowledgeUtils.createDefinition(definitionDTO, conceptID, authCookie);
            RestAssured
                    .given()
                            .accept(ContentType.JSON)
                            .pathParam("conceptID", conceptID)
                            .pathParam("definitionID", definitionID)
                            .pathParam("justificationID", 999)
                    .when()
                            .get("/concepts/{conceptID}/definitions/{definitionID}/justifications/{justificationID}")
                    .then()
                            .statusCode(HttpStatus.NOT_FOUND.value());

            KnowledgeUtils.deleteDefinition(conceptID, definitionID, authCookie);
            KnowledgeUtils.deleteConcept(conceptID, authCookie);
        }

        @Test
        @DisplayName("Should find a List of Definitions")
        void findAllWhenExists() {
            final ConceptDTO conceptDTO = new ConceptDTO("Software");
            final DefinitionDTO definitionDTO = new DefinitionDTO("Software definition", true);

            final Long conceptID = KnowledgeUtils.createConcept(conceptDTO, authCookie);
            final Long definitionID = KnowledgeUtils.createDefinition(definitionDTO, conceptID, authCookie);

            final JustificationDTO justificationDTO1 = new JustificationDTO("Software justification", true, null);
            final JustificationDTO justificationDTO2 = new JustificationDTO("Hardware justification", true, null);
            final JustificationDTO justificationDTO3 = new JustificationDTO("Functional Programming justification", true, null);
            final JustificationDTO justificationDTO4 = new JustificationDTO("Unix justification", true, null);
            final JustificationDTO justificationDTO5 = new JustificationDTO("Haskell justification", true, null);

            final Long justificationID1 = KnowledgeUtils.createJustification(justificationDTO1, conceptID, definitionID, authCookie);
            final Long justificationID2 = KnowledgeUtils.createJustification(justificationDTO2, conceptID, definitionID, authCookie);
            final Long justificationID3 = KnowledgeUtils.createJustification(justificationDTO3, conceptID, definitionID, authCookie);
            final Long justificationID4 = KnowledgeUtils.createJustification(justificationDTO4, conceptID, definitionID, authCookie);
            final Long justificationID5 = KnowledgeUtils.createJustification(justificationDTO5, conceptID, definitionID, authCookie);

            RestAssured
                    .given()
                            .pathParam("conceptID", conceptID)
                            .pathParam("definitionID", definitionID)
                            .accept(ContentType.JSON)
                    .when()
                            .get("/concepts/{conceptID}/definitions/{definitionID}/justifications/")
                    .then()
                            .statusCode(HttpStatus.OK.value())
                            .body("size()", greaterThanOrEqualTo(5));
            KnowledgeUtils.deleteJustification(conceptID, definitionID, justificationID1, authCookie);
            KnowledgeUtils.deleteJustification(conceptID, definitionID, justificationID2, authCookie);
            KnowledgeUtils.deleteJustification(conceptID, definitionID, justificationID3, authCookie);
            KnowledgeUtils.deleteJustification(conceptID, definitionID, justificationID4, authCookie);
            KnowledgeUtils.deleteJustification(conceptID, definitionID, justificationID5, authCookie);
            KnowledgeUtils.deleteDefinition(conceptID, definitionID, authCookie);
            KnowledgeUtils.deleteConcept(conceptID, authCookie);
        }

        @Test
        @DisplayName("Should find an empty list of Justifications")
        void findAllWhenNotExists() {
            final ConceptDTO conceptDTO = new ConceptDTO("Software");
            final DefinitionDTO definitionDTO = new DefinitionDTO("Software definition", true);

            final Long conceptID = KnowledgeUtils.createConcept(conceptDTO, authCookie);
            final Long definitionID = KnowledgeUtils.createDefinition(definitionDTO, conceptID, authCookie);

            RestAssured
                    .given()
                            .accept(ContentType.JSON)
                            .pathParams("conceptID", conceptID)
                            .pathParams("definitionID", definitionID)
                    .when()
                            .get("/concepts/{conceptID}/definitions/{definitionID}/justifications/")
                    .then()
                            .statusCode(HttpStatus.OK.value())
                            .body("isEmpty()", is(true));

            KnowledgeUtils.deleteDefinition(conceptID, definitionID, authCookie);
            KnowledgeUtils.deleteConcept(conceptID, authCookie);
        }

    }

    @Nested
    @DisplayName("PUT")
    class JustificationPut {

        @Test
        @DisplayName("Should update the Justification")
        void updateWhenExists() {
            final ConceptDTO conceptDTO = new ConceptDTO("Software");
            final DefinitionDTO definitionDTO = new DefinitionDTO("Software definition", true);

            final Long conceptID = KnowledgeUtils.createConcept(conceptDTO, authCookie);
            final Long definitionID = KnowledgeUtils.createDefinition(definitionDTO, conceptID, authCookie);

            final JustificationDTO justificationDTO1 = new JustificationDTO("Software justification", true, null);
            final JustificationDTO justificationDTO2 = new JustificationDTO("Hardware justification", true, null);

            final Long justificationID = KnowledgeUtils.createJustification(justificationDTO1, conceptID, definitionID, authCookie);

            RestAssured
                    .given()
                            .accept(ContentType.JSON)
                            .pathParam("conceptID", conceptID)
                            .pathParam("definitionID", definitionID)
                            .pathParam("justificationID", justificationID)

                    .when()
                            .get("/concepts/{conceptID}/definitions/{definitionID}/justifications/{justificationID}")
                    .then()
                            .statusCode(HttpStatus.OK.value())
                            .body("text", res -> equalTo(justificationDTO1.getText()))
                            .body("correct", res -> equalTo(justificationDTO1.getCorrect()));

            RestAssured
                    .given()
                            .cookie("AuthToken", authCookie)
                            .contentType("application/json")
                            .pathParam("conceptID", conceptID)
                            .pathParam("definitionID", definitionID)
                            .pathParam("justificationID", justificationID)
                            .body(justificationDTO2)
                    .when()
                            .put("/concepts/{conceptID}/definitions/{definitionID}/justifications/{justificationID}")
                    .then()
                            .statusCode(HttpStatus.NO_CONTENT.value());

            RestAssured
                    .given()
                            .accept(ContentType.JSON)
                            .pathParam("conceptID", conceptID)
                            .pathParam("definitionID", definitionID)
                            .pathParam("justificationID", justificationID)
                    .when()
                            .get("/concepts/{conceptID}/definitions/{definitionID}/justifications/{justificationID}")
                    .then()
                            .statusCode(HttpStatus.OK.value())
                            .body("text", res -> equalTo(justificationDTO2.getText()))
                            .body("correct", res -> equalTo(justificationDTO2.getCorrect()));

            KnowledgeUtils.deleteJustification(conceptID, definitionID, justificationID, authCookie);
            KnowledgeUtils.deleteDefinition(conceptID, definitionID, authCookie);
            KnowledgeUtils.deleteConcept(conceptID, authCookie);
        }

        @Test
        @DisplayName("Should throw an Exception")
        void updateWhenNotExists() {
            final ConceptDTO conceptDTO = new ConceptDTO("Software");
            final DefinitionDTO definitionDTO = new DefinitionDTO("Software definition", true);
            final JustificationDTO justificationDTO = new JustificationDTO("Software justification", true, null);

            final Long conceptID = KnowledgeUtils.createConcept(conceptDTO, authCookie);
            final Long definitionID = KnowledgeUtils.createDefinition(definitionDTO, conceptID, authCookie);


            RestAssured
                    .given()
                            .cookie("AuthToken", authCookie)
                            .contentType("application/json")
                            .pathParam("conceptID", conceptID)
                            .pathParam("definitionID", definitionID)
                            .pathParam("justificationID", 999)
                            .body(justificationDTO)
                    .when()
                            .put("/concepts/{conceptID}/definitions/{definitionID}/justifications/{justificationID}")
                    .then()
                            .statusCode(HttpStatus.NOT_FOUND.value());

            KnowledgeUtils.deleteDefinition(conceptID, definitionID, authCookie);
            KnowledgeUtils.deleteConcept(conceptID, authCookie);
        }
    }

    @Nested
    @DisplayName("DELETE")
    class JustificationDelete {
        @Test
        @DisplayName("Should delete the Justification")
        void deleteWhenExits() {
            final ConceptDTO conceptDTO = new ConceptDTO("Software");
            final DefinitionDTO definitionDTO = new DefinitionDTO("Software definition", true);
            final JustificationDTO justificationDTO = new JustificationDTO("Software justification", true, null);

            final Long conceptID = KnowledgeUtils.createConcept(conceptDTO, authCookie);
            final Long definitionID = KnowledgeUtils.createDefinition(definitionDTO, conceptID, authCookie);
            final Long justificationID = KnowledgeUtils.createJustification(justificationDTO, conceptID, definitionID, authCookie);

            RestAssured
                    .given()
                            .cookie("AuthToken", authCookie)
                            .accept(ContentType.JSON)
                            .pathParam("conceptID", conceptID)
                            .pathParam("definitionID", definitionID)
                            .pathParam("justificationID", justificationID)
                    .when()
                            .delete("/concepts/{conceptID}/definitions/{definitionID}/justifications/{justificationID}")
                    .then()
                            .statusCode(HttpStatus.NO_CONTENT.value());

            KnowledgeUtils.deleteDefinition(conceptID, definitionID, authCookie);
            KnowledgeUtils.deleteConcept(conceptID, authCookie);
        }

        @Test
        @DisplayName("Should throw an Exception")
        void deleteWhenNotExits() {
            final ConceptDTO conceptDTO = new ConceptDTO("Software");
            final DefinitionDTO definitionDTO = new DefinitionDTO("Software definition", true);

            final Long conceptID = KnowledgeUtils.createConcept(conceptDTO, authCookie);
            final Long definitionID = KnowledgeUtils.createDefinition(definitionDTO, conceptID, authCookie);

            RestAssured
                    .given()
                             .cookie("AuthToken", authCookie)
                             .accept(ContentType.JSON)
                             .pathParam("conceptID", conceptID)
                             .pathParam("definitionID", definitionID)
                             .pathParam("justificationID", 9999)
                     .when()
                             .delete("/concepts/{conceptID}/definitions/{definitionID}/justifications/{justificationID}")
                     .then()
                             .statusCode(HttpStatus.NOT_FOUND.value());

            KnowledgeUtils.deleteDefinition(conceptID, definitionID, authCookie);
            KnowledgeUtils.deleteConcept(conceptID, authCookie);
        }
    }
}
