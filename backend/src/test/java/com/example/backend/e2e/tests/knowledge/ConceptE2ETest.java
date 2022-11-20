package com.example.backend.e2e.tests.knowledge;

import com.example.backend.e2e.resources.knowledge.concept.dto.ConceptDTO;
import com.example.backend.e2e.util.KnowledgeUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.example.backend.e2e.util.GetAuthToken.getAuthCookie;
import static org.hamcrest.Matchers.*;

public class ConceptE2ETest {

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
    class ConceptPost {
        @Test
        @DisplayName("Should create a Concept giving a basic DTO")
        void createWithCorrectDTO() {
            final ConceptDTO conceptDTO = new ConceptDTO("Software");

            final Integer conceptID = RestAssured
                    .given()
                            .cookie("AuthToken", authCookie)
                            .contentType("application/json")
                            .body(conceptDTO)
                    .when()
                            .post("/concepts/")
                    .then()
                            .statusCode(HttpStatus.CREATED.value())
                            .contentType(ContentType.JSON)
                            .body("text", res -> equalTo("Software"))
                            .body("", res -> hasKey("id"))
                    .extract()
                            .path("id");

            KnowledgeUtils.deleteConcept(conceptID.longValue(), authCookie);
        }

        @Test
        @DisplayName("Should not create a Concept giving a wrong DTO")
        void createWithWrongDTO() {
            final ConceptDTO wrongConceptDTO = new ConceptDTO();

            RestAssured
                    .given()
                            .cookie("AuthToken", authCookie)
                            .contentType("application/json")
                            .body(wrongConceptDTO)
                    .when()
                            .post("/concepts/")
                    .then()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .contentType(ContentType.JSON)
                            .body("message", res -> equalTo("Field text in Concept DTO is mandatory"))
                            .body("httpStatus", res -> equalTo("BAD_REQUEST"));
        }
    }

    @Nested
    @DisplayName("GET")
    class ConceptGet {
        @Test
        @DisplayName("Should find a Concept with the given ID")
        void findOneWhenExists() {
            final ConceptDTO conceptDTO = new ConceptDTO("Software");

            final Long conceptID = KnowledgeUtils.createConcept(conceptDTO, authCookie);

            RestAssured
                    .given()
                            .accept(ContentType.JSON)
                            .pathParam("conceptID", conceptID)
                    .when()
                            .get("/concepts/" + "{conceptID}")
                    .then()
                            .statusCode(HttpStatus.OK.value())
                            .body("text", res -> equalTo("Software"))
                            .body("", res -> hasKey("id"))
                    .extract()
                            .path("id");

            KnowledgeUtils.deleteConcept(conceptID, authCookie);
        }

        @Test
        @DisplayName("Should not find a Concept with the given ID")
        void findOneWhenNotExists() {
            final int conceptID = 999;

            RestAssured
                    .given()
                            .accept(ContentType.JSON)
                            .pathParam("conceptID", conceptID)
                    .when()
                            .get("/concepts/" + "{conceptID}")
                    .then()
                            .statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        @DisplayName("Should find a Page of Concepts")
        void findAllWhenExists() {
            final ConceptDTO conceptDTO1 = new ConceptDTO("Software");
            final ConceptDTO conceptDTO2 = new ConceptDTO("Hardware");
            final ConceptDTO conceptDTO3 = new ConceptDTO("Functional Programming");
            final ConceptDTO conceptDTO4 = new ConceptDTO("Unix");
            final ConceptDTO conceptDTO5 = new ConceptDTO("Haskell");

            final Long conceptID1 = KnowledgeUtils.createConcept(conceptDTO1, authCookie);
            final Long conceptID2 = KnowledgeUtils.createConcept(conceptDTO2, authCookie);
            final Long conceptID3 = KnowledgeUtils.createConcept(conceptDTO3, authCookie);
            final Long conceptID4 = KnowledgeUtils.createConcept(conceptDTO4, authCookie);
            final Long conceptID5 = KnowledgeUtils.createConcept(conceptDTO5, authCookie);

            RestAssured
                    .given()
                            .accept(ContentType.JSON)
                            .queryParam("page", 0)
                    .when()
                            .get("/concepts/")
                    .then()
                            .statusCode(HttpStatus.OK.value())
                            .body("", res -> hasKey("content"))
                            .body("content.size()", res -> is(greaterThanOrEqualTo(5)))
                            .body("", res -> hasKey("pageable"))
                            .body("", res -> hasKey("totalPages"));

            KnowledgeUtils.deleteConcept(conceptID1, authCookie);
            KnowledgeUtils.deleteConcept(conceptID2, authCookie);
            KnowledgeUtils.deleteConcept(conceptID3, authCookie);
            KnowledgeUtils.deleteConcept(conceptID4, authCookie);
            KnowledgeUtils.deleteConcept(conceptID5, authCookie);
        }
    }

    @Nested
    @DisplayName("PUT")
    class ConceptPut {
        @Test
        @DisplayName("Should update the Concept")
        void updateWhenExists() {
            final ConceptDTO conceptDTO1 = new ConceptDTO("Software");
            final ConceptDTO conceptDTO2 = new ConceptDTO("Hardware");

            final Long conceptID = KnowledgeUtils.createConcept(conceptDTO1, authCookie);

            RestAssured
                    .given()
                            .accept(ContentType.JSON)
                            .pathParam("conceptID", conceptID)
                    .when()
                            .get("/concepts/" + "{conceptID}")
                    .then()
                            .statusCode(HttpStatus.OK.value())
                            .body("text", res -> equalTo(conceptDTO1.getText()));

            RestAssured
                    .given()
                            .contentType("application/json")
                            .pathParam("conceptID", conceptID)
                            .body(conceptDTO2)
                    .when()
                            .cookie("AuthToken", authCookie)
                            .put("/concepts/" + "{conceptID}")
                    .then()
                            .statusCode(HttpStatus.NO_CONTENT.value());

            RestAssured
                    .given()
                            .accept(ContentType.JSON)
                            .pathParam("conceptID", conceptID)
                    .when()
                            .get("/concepts/" + "{conceptID}")
                    .then()
                            .statusCode(HttpStatus.OK.value())
                            .body("text", res -> equalTo(conceptDTO2.getText()));

            KnowledgeUtils.deleteConcept(conceptID, authCookie);
        }

        @Test
        @DisplayName("Should throw an Exception")
        void updateWhenNotExists() {
            final ConceptDTO conceptDTO1 = new ConceptDTO("Software");
            final ConceptDTO conceptDTO2 = new ConceptDTO("Hardware");

            final Long conceptID = KnowledgeUtils.createConcept(conceptDTO1, authCookie);

            RestAssured
                    .given()
                        .cookie("AuthToken", authCookie)
                        .contentType("application/json")
                        .pathParam("conceptID", conceptID + 9999)
                        .body(conceptDTO2)
                    .when()
                            .put("/concepts/" + "{conceptID}")
                    .then()
                            .statusCode(HttpStatus.NOT_FOUND.value());

            KnowledgeUtils.deleteConcept(conceptID, authCookie);
        }
    }

    @Nested
    @DisplayName("DELETE")
    class ConceptDelete {
        @Test
        @DisplayName("Should delete the Concept")
        void deleteWhenExits() {
            final ConceptDTO conceptDTO = new ConceptDTO("Software");

            final Long conceptID = KnowledgeUtils.createConcept(conceptDTO, authCookie);

            RestAssured
                    .given()
                            .cookie("AuthToken", authCookie)
                            .accept(ContentType.JSON)
                            .pathParam("conceptID", conceptID)
                    .when()
                            .delete("/concepts/" + "{conceptID}")
                    .then()
                            .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @Test
        @DisplayName("Should throw an Exception")
        void deleteWhenNotExits() {
            RestAssured
                    .given()
                            .cookie("AuthToken", authCookie)
                            .accept(ContentType.JSON)
                            .pathParam("conceptID", 999)
                    .when()
                            .delete("/concepts/" + "{conceptID}")
                    .then()
                            .statusCode(HttpStatus.NOT_FOUND.value());

        }
    }
}
