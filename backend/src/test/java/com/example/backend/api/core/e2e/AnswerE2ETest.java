package com.example.backend.api.core.e2e;

import com.example.backend.api.core.answer.dto.AnswerReqDTO;
import com.example.backend.api.core.concept.dto.ConceptReqDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AnswerE2ETest {

    public static int CONCEPT_ID = 1;
    public final String BASE_URL = "/concepts/" + CONCEPT_ID + "/answers/";



    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        CONCEPT_ID = createConcept(new ConceptReqDTO("Software")).extract().path("id");
    }

    @Nested
    @DisplayName("POST")
    class AnswerPost {

        @Test
        @DisplayName("(Create) Should create an answer if the DTO is correct")
        void createWithCorrectDTO() {
            final AnswerReqDTO answerReqDTO = new AnswerReqDTO("Software answer", true);

            given()
                    .contentType("application/json")
                    .body(answerReqDTO)
            .when()
                    .post(BASE_URL)
            .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .contentType(ContentType.JSON)
                    .body("text", res -> equalTo("Software answer"))
                    .body("isCorrect", res -> equalTo(true));
        }

        @Test
        @DisplayName("(Create) Should not create an Answer if the DTO is malformed")
        void createWithWrongDTO() {
            final AnswerReqDTO wrongAnswerReqDTO = new AnswerReqDTO("");

            given()
                    .contentType("application/json")
                    .body(wrongAnswerReqDTO)
            .when()
                    .post(BASE_URL)
            .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .contentType(ContentType.JSON)
                    .body("message", res -> equalTo("Field text in Answer DTO is mandatory"))
                    .body("httpStatus", res -> equalTo("BAD_REQUEST"));
        }

        @Test
        @DisplayName("(Create) Should create the Answer only in the specified Concept")
        void createAnswerInTheCorrectConcept() {
            final ConceptReqDTO conceptReqDTO = new ConceptReqDTO("Hardware");
            final AnswerReqDTO answerReqDTO = new AnswerReqDTO("Software answer", true);

            final int conceptId = createConcept(conceptReqDTO).extract().path("id");
            final int answerId = createAnswer(answerReqDTO, conceptId).extract().path("id");

            // Check that the Answer is in the first Concept
            given()
                    .accept(ContentType.JSON)
                    .pathParam("conceptId", conceptId)
            .when()
                    .get("/concepts/{conceptId}")
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("answers._embedded.answerResDTOList[0].id", res -> is(answerId));

            // Check that the Answer is not in the second Concept
            given()
                    .accept(ContentType.JSON)
                    .pathParam("conceptId", conceptId + 999)
            .when()
                    .get("/concepts/{conceptId}")
            .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
        }
    }

    @Nested
    @DisplayName("GET")
    class AnswerGet {

        @Test
        @DisplayName("(FindOne) Should find an Answer with the given id")
        void findOneWhenExists() {
            final AnswerReqDTO answerReqDTO = new AnswerReqDTO("Software answer", true);

            final int id = createAnswer(answerReqDTO, CONCEPT_ID).extract().path("id");

            given()
                    .accept(ContentType.JSON)
                    .pathParam("id", id)
            .when()
                    .get(BASE_URL + "{id}")
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("text", res -> equalTo("Software answer"))
                    .body("isCorrect", res -> equalTo(true))
                    .body("_links", res -> hasKey("self"))
                    .body("_links", res -> hasKey("answers"));
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
            final AnswerReqDTO answerReqDTO1 = new AnswerReqDTO("Software answer", true);
            final AnswerReqDTO answerReqDTO2 = new AnswerReqDTO("Hardware answer", true);
            final AnswerReqDTO answerReqDTO3 = new AnswerReqDTO("Functional Programming answer", false);
            final AnswerReqDTO answerReqDTO4 = new AnswerReqDTO(" answer", false);
            final AnswerReqDTO answerReqDTO5 = new AnswerReqDTO("Haskell answer", true);

            createAnswer(answerReqDTO1, CONCEPT_ID);
            createAnswer(answerReqDTO2, CONCEPT_ID);
            createAnswer(answerReqDTO3, CONCEPT_ID);
            createAnswer(answerReqDTO4, CONCEPT_ID);
            createAnswer(answerReqDTO5, CONCEPT_ID);

            given()
                    .accept(ContentType.JSON)
            .when()
                    .get(BASE_URL)
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("_embedded.answerResDTOList.size()", greaterThanOrEqualTo(5));
        }

        @Test
        @DisplayName("(FindAll) Should find an empty List of Answers")
        void findAllWhenNotExists() {
            final ConceptReqDTO conceptReqDTO = new ConceptReqDTO("Software");

            final int conceptId = createConcept(conceptReqDTO).extract().path("id");

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
    class AnswerPut {

        @Test
        @DisplayName("(UpdateOne) Should update the Answer")
        void updateWhenExists() {
            final AnswerReqDTO answerReqDTO1 = new AnswerReqDTO("Software answer", true);
            final AnswerReqDTO answerReqDTO2 = new AnswerReqDTO("Hardware answer", true);


            final int id = createAnswer(answerReqDTO1, CONCEPT_ID).extract().path("id");

            // Check the initial Answer content
            given()
                    .accept(ContentType.JSON)
                    .pathParam("id", id)
            .when()
                    .get(BASE_URL + "{id}")
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("text", res -> equalTo(answerReqDTO1.getText()))
                    .body("isCorrect", res -> equalTo(answerReqDTO1.getIsCorrect()));

            // Update the Answer
            given()
                    .contentType("application/json")
                    .pathParam("id", id)
                    .body(answerReqDTO2)
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
                    .body("text", res -> equalTo(answerReqDTO2.getText()))
                    .body("isCorrect", res -> equalTo(answerReqDTO2.getIsCorrect()));
        }

        @Test
        @DisplayName("(UpdateOne) Should throw an exception")
        void updateWhenNotExists() {
            final ConceptReqDTO conceptReqDTO1 = new ConceptReqDTO("Software");
            final AnswerReqDTO answerReqDTO1 = new AnswerReqDTO("Software answer", true);

            createConcept(conceptReqDTO1).extract().path("id");

            given()
                    .contentType("application/json")
                    .pathParam("id", 9999)
                    .body(answerReqDTO1)
            .when()
                    .put(BASE_URL + "{id}")
            .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
        }
    }

    @Nested
    @DisplayName("DELETE")
    class AnswerDelete {
        @Test
        @DisplayName("(Remove) Should delete the Answer")
        void deleteWhenExits() {
            final AnswerReqDTO answerReqDTO = new AnswerReqDTO("Software answer", true);

            final int answerId = createAnswer(answerReqDTO, CONCEPT_ID).extract().path("id");

            given()
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
     * @param answerReqDTO The Answer to be created.
     * @param conceptId The Concept id where the Answer should be created.
     * @return The response body.
     */
    private ValidatableResponse createAnswer(AnswerReqDTO answerReqDTO, int conceptId) {
        return
                given()
                        .contentType("application/json")
                        .body(answerReqDTO)
                .when()
                        .post("/concepts/" + conceptId + "/answers/")
                .then();
    }

    /**
     * Create a Concept.
     *
     * @param conceptReqDTO The Concept to be created.
     * @return The response body.
     */
    private static ValidatableResponse createConcept(ConceptReqDTO conceptReqDTO) {
        return
                given()
                        .contentType("application/json")
                        .body(conceptReqDTO)
                .when()
                        .post("/concepts/")
                .then();
    }

}
