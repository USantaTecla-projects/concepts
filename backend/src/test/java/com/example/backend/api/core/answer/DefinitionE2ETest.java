package com.example.backend.api.core.answer;

import com.example.backend.api.resources.knowledge.definition.dto.DefinitionDTO;
import com.example.backend.api.resources.knowledge.concept.dto.ConceptDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.example.backend.util.GetAuthToken.getAuthCookie;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class DefinitionE2ETest {

    public static int CONCEPT_ID = 1;
    public final String BASE_URL = "/concepts/" + CONCEPT_ID + "/answers/";
    private static String authCookie;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://localhost";
        RestAssured.port = 8443;
        RestAssured.useRelaxedHTTPSValidation();

        authCookie = getAuthCookie();
        CONCEPT_ID = createConcept(new ConceptDTO("Software")).extract().path("id");
    }

    @Nested
    @DisplayName("POST")
    class DefinitionPost {

        @Test
        @DisplayName("(Create) Should create an answer if the DTO is correct")
        void createWithCorrectDTO() {

            final DefinitionDTO definitionDTO = new DefinitionDTO("Software answer", true);

            given()
                    .cookie("AuthToken",authCookie)
                    .contentType("application/json")
                    .body(definitionDTO)
            .when()
                    .post(BASE_URL)
            .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .contentType(ContentType.JSON)
                    .body("text", res -> equalTo("Software answer"))
                    .body("correct", res -> equalTo(true));
        }

        @Test
        @DisplayName("(Create) Should not create an Answer if the DTO is malformed")
        void createWithWrongDTO() {
            final DefinitionDTO wrongDefinitionDTO = new DefinitionDTO();

            given()
                    .cookie("AuthToken",authCookie)
                    .contentType("application/json")
                    .body(wrongDefinitionDTO)
            .when()
                    .post(BASE_URL)
            .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .contentType(ContentType.JSON)
                    .body("message", res -> equalTo("Field text in Answer DTO is mandatory"))
                    .body("httpStatus", res -> equalTo("BAD_REQUEST"));
        }

        @Test
        @DisplayName("(Create) Should create the answer in the specified concept")
        void createAnswerInTheCorrectConcept() {
            final ConceptDTO conceptDTO = new ConceptDTO("Hardware");
            final DefinitionDTO definitionDTO = new DefinitionDTO("Software answer", true);

            final int conceptId = createConcept(conceptDTO).extract().path("id");
            final int answerId = createAnswer(definitionDTO, conceptId).extract().path("id");

            // Check that the Answer is in the first Concept
            given()
                    .cookie("AuthToken",authCookie)
                    .accept(ContentType.JSON)
                    .pathParam("conceptId", conceptId)
            .when()
                    .get("/concepts/{conceptId}")
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("answers[0].id", res -> is(answerId));

        }
    }

    @Nested
    @DisplayName("GET")
    class DefinitionGet {

        @Test
        @DisplayName("(FindOne) Should find an Answer with the given id")
        void findOneWhenExists() {
            final DefinitionDTO definitionDTO = new DefinitionDTO("Software answer", true);

            final int id = createAnswer(definitionDTO, CONCEPT_ID).extract().path("id");

            given()
                    .accept(ContentType.JSON)
                    .pathParam("id", id)
            .when()
                    .get(BASE_URL + "{id}")
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("text", res -> equalTo("Software answer"))
                    .body("correct", res -> equalTo(true))
                    .body("conceptId", res -> equalTo(CONCEPT_ID));
        }

        @Test
        @DisplayName("(FindOne) Should not find an Answer with the given id")
        void findOneWhenNotExists() {
            final int id = 999;

            given()
                    .accept(ContentType.JSON)
                    .pathParam("id", id)
            .when()
                    .get(BASE_URL + "{id}")
            .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        @DisplayName("(FindAll) Should find a List of Answers")
        void findAllWhenExists() {
            final DefinitionDTO definitionDTO1 = new DefinitionDTO("Software answer", true);
            final DefinitionDTO definitionDTO2 = new DefinitionDTO("Hardware answer", true);
            final DefinitionDTO definitionDTO3 = new DefinitionDTO("Functional Programming answer", false);
            final DefinitionDTO definitionDTO4 = new DefinitionDTO(" answer", false);
            final DefinitionDTO definitionDTO5 = new DefinitionDTO("Haskell answer", true);

            createAnswer(definitionDTO1, CONCEPT_ID);
            createAnswer(definitionDTO2, CONCEPT_ID);
            createAnswer(definitionDTO3, CONCEPT_ID);
            createAnswer(definitionDTO4, CONCEPT_ID);
            createAnswer(definitionDTO5, CONCEPT_ID);

            given()
                    .accept(ContentType.JSON)
            .when()
                    .get(BASE_URL)
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", greaterThanOrEqualTo(5));
        }

        @Test
        @DisplayName("(FindAll) Should find an empty List of Answers")
        void findAllWhenNotExists() {
            final ConceptDTO conceptDTO = new ConceptDTO("Software");

            final int conceptId = createConcept(conceptDTO).extract().path("id");

            given()
                    .accept(ContentType.JSON)
            .when()
                    .get("/concepts/" + conceptId + "/answers/")
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("isEmpty()", is(true));

        }

    }

    @Nested
    @DisplayName("PUT")
    class DefinitionPut {

        @Test
        @DisplayName("(UpdateOne) Should update the Answer")
        void updateWhenExists() {
            final DefinitionDTO definitionDTO1 = new DefinitionDTO("Software answer", true);
            final DefinitionDTO definitionDTO2 = new DefinitionDTO("Hardware answer", true);


            final int id = createAnswer(definitionDTO1, CONCEPT_ID).extract().path("id");

            // Check the initial Answer content
            given()
                    .accept(ContentType.JSON)
                    .pathParam("id", id)
            .when()
                    .get(BASE_URL + "{id}")
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("text", res -> equalTo(definitionDTO1.getText()))
                    .body("correct", res -> equalTo(definitionDTO1.getCorrect()));

            // Update the Answer
            given()
                    .cookie("AuthToken",authCookie)
                    .contentType("application/json")
                    .pathParam("id", id)
                    .body(definitionDTO2)
            .when()
                    .put(BASE_URL + "{id}")
            .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());

            // Check the updated Answer content
            given()
                    .accept(ContentType.JSON)
                    .pathParam("id", id)
            .when()
                    .get(BASE_URL + "{id}")
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("text", res -> equalTo(definitionDTO2.getText()))
                    .body("correct", res -> equalTo(definitionDTO2.getCorrect()));
        }

        @Test
        @DisplayName("(UpdateOne) Should throw an exception")
        void updateWhenNotExists() {
            final ConceptDTO conceptDTO1 = new ConceptDTO("Software");
            final DefinitionDTO definitionDTO1 = new DefinitionDTO("Software answer", true);

            createConcept(conceptDTO1).extract().path("id");

            given()
                    .cookie("AuthToken",authCookie)
                    .contentType("application/json")
                    .pathParam("id", 9999)
                    .body(definitionDTO1)
            .when()
                    .put(BASE_URL + "{id}")
            .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
        }
    }

    @Nested
    @DisplayName("DELETE")
    class DefinitionDelete {
        @Test
        @DisplayName("(Remove) Should delete the Answer")
        void deleteWhenExits() {
            final DefinitionDTO definitionDTO = new DefinitionDTO("Software answer", true);

            final int answerId = createAnswer(definitionDTO, CONCEPT_ID).extract().path("id");

            given()
                    .cookie("AuthToken",authCookie)
                    .accept(ContentType.JSON)
                    .pathParam("answerId", answerId)
            .when()
                    .delete(BASE_URL + "{answerId}")
            .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());

        }

        @Test
        @DisplayName("(Remove) Should throw an Exception")
        void deleteWhenNotExits() {
            given()
                    .cookie("AuthToken",authCookie)
                    .accept(ContentType.JSON)
                    .pathParam("answerId", 9999)
            .when()
                    .delete(BASE_URL + "{answerId}")
            .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());

        }
    }

    /**
     * Create an Answer in a Concept by its id.
     *
     * @param definitionDTO The Answer to be created.
     * @param conceptId The Concept id where the Answer should be created.
     * @return The response body.
     */
    private ValidatableResponse createAnswer(DefinitionDTO definitionDTO, int conceptId) {
        return
                given()
                        .cookie("AuthToken",authCookie)
                        .contentType("application/json")
                        .body(definitionDTO)
                .when()
                        .post("/concepts/" + conceptId + "/answers/")
                .then();
    }

    /**
     * Create a Concept.
     *
     * @param conceptDTO The Concept to be created.
     * @return The response body.
     */
    private static ValidatableResponse createConcept(ConceptDTO conceptDTO) {
        return
                given()
                        .cookie("AuthToken",authCookie)
                        .contentType("application/json")
                        .body(conceptDTO)
                .when()
                        .post("/concepts/")
                .then();
    }

}
