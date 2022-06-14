package com.example.backend.api.core.e2e;

import com.example.backend.api.core.answer.dto.AnswerDTO;
import com.example.backend.api.core.concept.dto.ConceptDTO;
import com.example.backend.api.core.justification.dto.JustificationDTO;
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
import static org.hamcrest.Matchers.equalTo;

public class JustificationE2ETest {

    public static int CONCEPT_ID = 1;
    public static int ANSWER_ID = 2;
    public final String BASE_URL = "/concepts/" + CONCEPT_ID + "/answers/" + ANSWER_ID + "/justifications/";

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        CONCEPT_ID = createConcept(new ConceptDTO("Software")).extract().path("id");
        ANSWER_ID = createAnswer(new AnswerDTO("Software answer", true), CONCEPT_ID).extract().path("id");
    }


    @Nested
    @DisplayName("POST")
    class JustificationPost {

        @Test
        @DisplayName("(Create) Should create a justification if the DTO is correct")
        void createWithCorrectDTO() {
            final JustificationDTO justificationDTO = new JustificationDTO("Software justification", true, null);

            given()
                    .contentType("application/json")
                    .body(justificationDTO)
                    .when()
                    .post(BASE_URL)
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .contentType(ContentType.JSON)
                    .body("text", res -> equalTo("Software justification"))
                    .body("correct", res -> equalTo(true));
        }

        @Test
        @DisplayName("(Create) Should not create a justification if the DTO is malformed")
        void createWithWrongDTO() {
            final JustificationDTO wrongJustificationDTO = new JustificationDTO();

            given()
                    .contentType("application/json")
                    .body(wrongJustificationDTO)
                    .when()
                    .post(BASE_URL)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .contentType(ContentType.JSON)
                    .body("message", res -> equalTo("Field text in Justification DTO is mandatory"))
                    .body("httpStatus", res -> equalTo("BAD_REQUEST"));
        }

        @Test
        @DisplayName("(Create) Should create the justification in the specified answer")
        void createAnswerInTheCorrectConcept() {
            final ConceptDTO conceptDTO = new ConceptDTO("Software");
            final AnswerDTO answerDTO = new AnswerDTO("Software answer", true);
            final JustificationDTO justificationDTO = new JustificationDTO("Software justification", true, null);


            final int conceptId = createConcept(conceptDTO).extract().path("id");
            final int answerId = createAnswer(answerDTO, conceptId).extract().path("id");
            final int justificationId = createJustification(justificationDTO, conceptId, answerId).extract().path("id");

            // Check that the justification is in the first answer
            given()
                    .accept(ContentType.JSON)
                    .pathParams("conceptId", conceptId)
                    .pathParam("answerId", answerId)
                    .when()
                    .get("/concepts/{conceptId}/answers/{answerId}")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("justifications[0].id", res -> is(justificationId));

        }
    }

    @Nested
    @DisplayName("GET")
    class JustificationGet {

        @Test
        @DisplayName("(FindOne) Should find a justification with the given id")
        void findOneWhenExists() {
            final JustificationDTO justificationDTO = new JustificationDTO("Software justification", true, null);

            final int justificationId = createJustification(justificationDTO, CONCEPT_ID, ANSWER_ID).extract().path("id");

            given()
                    .accept(ContentType.JSON)
                    .pathParam("justificationId", justificationId)
                    .when()
                    .get(BASE_URL + "{justificationId}")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("text", res -> equalTo("Software justification"))
                    .body("correct", res -> equalTo(true))
                    .body("conceptId", res -> equalTo(CONCEPT_ID));
        }

        @Test
        @DisplayName("(FindOne) Should not find an Answer with the given id")
        void findOneWhenNotExists() {
            final int wrongJustificationId = 999;

            given()
                    .accept(ContentType.JSON)
                    .pathParam("wrongJustificationId", wrongJustificationId)
                    .when()
                    .get(BASE_URL + "{wrongJustificationId}")
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        @DisplayName("(FindAll) Should find a List of Answers")
        void findAllWhenExists() {
            final JustificationDTO justificationDTO1 = new JustificationDTO("Software justification", true, null);
            final JustificationDTO justificationDTO2 = new JustificationDTO("Hardware justification", true, null);
            final JustificationDTO justificationDTO3 = new JustificationDTO("Functional Programming justification", true, null);
            final JustificationDTO justificationDTO4 = new JustificationDTO("Unix justification", true, null);
            final JustificationDTO justificationDTO5 = new JustificationDTO("Haskell justification", true, null);

            createJustification(justificationDTO1, CONCEPT_ID, ANSWER_ID);
            createJustification(justificationDTO2, CONCEPT_ID, ANSWER_ID);
            createJustification(justificationDTO3, CONCEPT_ID, ANSWER_ID);
            createJustification(justificationDTO4, CONCEPT_ID, ANSWER_ID);
            createJustification(justificationDTO5, CONCEPT_ID, ANSWER_ID);

            given()
                    .accept(ContentType.JSON)
                    .when()
                    .get(BASE_URL)
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", greaterThanOrEqualTo(5));
        }

        @Test
        @DisplayName("(FindAll) Should find an empty list of justifications")
        void findAllWhenNotExists() {
            final ConceptDTO conceptDTO = new ConceptDTO("Software Concept");
            final AnswerDTO answerDTO = new AnswerDTO("Software answer", true);


            final int conceptId = createConcept(conceptDTO).extract().path("id");
            final int answerId = createAnswer(answerDTO, conceptId).extract().path("id");

            given()
                    .accept(ContentType.JSON)
                    .pathParams("conceptId", conceptId)
                    .pathParams("answerId", answerId)
                    .when()
                    .get("/concepts/{conceptId}/answers/{answerId}/justifications/")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("isEmpty()", is(true));

        }

    }

    @Nested
    @DisplayName("PUT")
    class JustificationPut {

        @Test
        @DisplayName("(UpdateOne) Should update the justification")
        void updateWhenExists() {
            final JustificationDTO justificationDTO1 = new JustificationDTO("Software justification", true, null);
            final JustificationDTO justificationDTO2 = new JustificationDTO("Hardware justification", true, null);

            final int justificationId = createJustification(justificationDTO1, CONCEPT_ID, ANSWER_ID).extract().path("id");

            // Check the initial Answer content
            given()
                    .accept(ContentType.JSON)
                    .pathParam("justificationId", justificationId)
            .when()
                    .get(BASE_URL + "{justificationId}")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("text", res -> equalTo(justificationDTO1.getText()))
                    .body("correct", res -> equalTo(justificationDTO1.getIsCorrect()));

            // Update the Answer
            given()
                    .contentType("application/json")
                    .pathParam("justificationId", justificationId)
                    .body(justificationDTO2)
            .when()
                    .put(BASE_URL + "{justificationId}")
            .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());

            // Check the updated Answer content
            given()
                    .accept(ContentType.JSON)
                    .pathParam("justificationId", justificationId)
            .when()
                    .get(BASE_URL + "{justificationId}")
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("text", res -> equalTo(justificationDTO2.getText()))
                    .body("correct", res -> equalTo(justificationDTO2.getIsCorrect()));
        }

        @Test
        @DisplayName("(UpdateOne) Should throw an exception")
        void updateWhenNotExists() {
            final JustificationDTO justificationDTO = new JustificationDTO("Software justification", true, null);

            given()
                    .contentType("application/json")
                    .pathParam("justificationId", 9999)
                    .body(justificationDTO)
            .when()
                    .put(BASE_URL + "{justificationId}")
            .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
        }
    }

    @Nested
    @DisplayName("DELETE")
    class JustificationDelete {
        @Test
        @DisplayName("(Remove) Should delete the justification")
        void deleteWhenExits() {
            final JustificationDTO justificationDTO = new JustificationDTO("Software justification", true, null);
            final int justificationId = createJustification(justificationDTO, CONCEPT_ID,ANSWER_ID).extract().path("id");

            given()
                    .accept(ContentType.JSON)
                    .pathParam("justificationId", justificationId)
            .when()
                    .delete(BASE_URL + "{justificationId}")
            .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());

        }

        @Test
        @DisplayName("(Remove) Should throw an Exception")
        void deleteWhenNotExits() {
            given()
                    .accept(ContentType.JSON)
                    .pathParam("justificationId", 9999)
            .when()
                    .delete(BASE_URL + "{justificationId}")
            .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());

        }
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
                        .contentType("application/json")
                        .body(conceptDTO)
                        .when()
                        .post("/concepts/")
                        .then();
    }

    private static ValidatableResponse createAnswer(AnswerDTO answerDTO, long conceptId) {
        return
                given()
                        .contentType("application/json")
                        .body(answerDTO)
                        .when()
                        .post("/concepts/" + conceptId + "/answers/")
                        .then();
    }

    private static ValidatableResponse createJustification(JustificationDTO justificationDTO, long conceptId, long answerId) {
        return
                given()
                        .contentType("application/json")
                        .body(justificationDTO)
                        .when()
                        .post("/concepts/" + conceptId + "/answers/" + answerId + "/justifications/")
                        .then();
    }

}
