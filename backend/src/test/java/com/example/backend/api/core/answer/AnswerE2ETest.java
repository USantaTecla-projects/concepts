package com.example.backend.api.core.answer;

import com.example.backend.api.resources.core.answer.dto.AnswerDTO;
import com.example.backend.api.resources.core.concept.dto.ConceptDTO;
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

public class AnswerE2ETest {

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
    class AnswerPost {

        @Test
        @DisplayName("(Create) Should create an answer if the DTO is correct")
        void createWithCorrectDTO() {

            final AnswerDTO answerDTO = new AnswerDTO("Software answer", true);

            given()
                    .cookie("AuthToken",authCookie)
                    .contentType("application/json")
                    .body(answerDTO)
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
            final AnswerDTO wrongAnswerDTO = new AnswerDTO("");

            given()
                    .cookie("AuthToken",authCookie)
                    .contentType("application/json")
                    .body(wrongAnswerDTO)
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
            final AnswerDTO answerDTO = new AnswerDTO("Software answer", true);

            final int conceptId = createConcept(conceptDTO).extract().path("id");
            final int answerId = createAnswer(answerDTO, conceptId).extract().path("id");

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
    class AnswerGet {

        @Test
        @DisplayName("(FindOne) Should find an Answer with the given id")
        void findOneWhenExists() {
            final AnswerDTO answerDTO = new AnswerDTO("Software answer", true);

            final int id = createAnswer(answerDTO, CONCEPT_ID).extract().path("id");

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
            final AnswerDTO answerDTO1 = new AnswerDTO("Software answer", true);
            final AnswerDTO answerDTO2 = new AnswerDTO("Hardware answer", true);
            final AnswerDTO answerDTO3 = new AnswerDTO("Functional Programming answer", false);
            final AnswerDTO answerDTO4 = new AnswerDTO(" answer", false);
            final AnswerDTO answerDTO5 = new AnswerDTO("Haskell answer", true);

            createAnswer(answerDTO1, CONCEPT_ID);
            createAnswer(answerDTO2, CONCEPT_ID);
            createAnswer(answerDTO3, CONCEPT_ID);
            createAnswer(answerDTO4, CONCEPT_ID);
            createAnswer(answerDTO5, CONCEPT_ID);

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
    class AnswerPut {

        @Test
        @DisplayName("(UpdateOne) Should update the Answer")
        void updateWhenExists() {
            final AnswerDTO answerDTO1 = new AnswerDTO("Software answer", true);
            final AnswerDTO answerDTO2 = new AnswerDTO("Hardware answer", true);


            final int id = createAnswer(answerDTO1, CONCEPT_ID).extract().path("id");

            // Check the initial Answer content
            given()
                    .accept(ContentType.JSON)
                    .pathParam("id", id)
            .when()
                    .get(BASE_URL + "{id}")
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("text", res -> equalTo(answerDTO1.getText()))
                    .body("correct", res -> equalTo(answerDTO1.getIsCorrect()));

            // Update the Answer
            given()
                    .cookie("AuthToken",authCookie)
                    .contentType("application/json")
                    .pathParam("id", id)
                    .body(answerDTO2)
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
                    .body("text", res -> equalTo(answerDTO2.getText()))
                    .body("correct", res -> equalTo(answerDTO2.getIsCorrect()));
        }

        @Test
        @DisplayName("(UpdateOne) Should throw an exception")
        void updateWhenNotExists() {
            final ConceptDTO conceptDTO1 = new ConceptDTO("Software");
            final AnswerDTO answerDTO1 = new AnswerDTO("Software answer", true);

            createConcept(conceptDTO1).extract().path("id");

            given()
                    .cookie("AuthToken",authCookie)
                    .contentType("application/json")
                    .pathParam("id", 9999)
                    .body(answerDTO1)
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
            final AnswerDTO answerDTO = new AnswerDTO("Software answer", true);

            final int answerId = createAnswer(answerDTO, CONCEPT_ID).extract().path("id");

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
     * @param answerDTO The Answer to be created.
     * @param conceptId The Concept id where the Answer should be created.
     * @return The response body.
     */
    private ValidatableResponse createAnswer(AnswerDTO answerDTO, int conceptId) {
        return
                given()
                        .cookie("AuthToken",authCookie)
                        .contentType("application/json")
                        .body(answerDTO)
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
