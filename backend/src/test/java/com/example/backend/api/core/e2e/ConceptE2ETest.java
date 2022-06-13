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

public class ConceptE2ETest {

    final String BASE_URL = "/concepts/";

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Nested
    @DisplayName("POST")
    class ConceptPost {
        @Test
        @DisplayName("(Create) Should create a Concept giving a basic DTO")
        void createWithCorrectDTO() {
            final ConceptReqDTO conceptReqDTO = new ConceptReqDTO("Software");

            given()
                    .contentType("application/json")
                    .body(conceptReqDTO)
            .when()
                    .post(BASE_URL)
            .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .contentType(ContentType.JSON)
                    .body("text", res -> equalTo("Software"))
                    .body("_links", res -> hasKey("self"))
                    .body("_links", res -> hasKey("concepts"));
        }

        @Test
        @DisplayName("(Create) Should not create a Concept giving a wrong DTO")
        void createWithWrongDTO() {
            final ConceptReqDTO wrongConceptReqDTO = new ConceptReqDTO();

            given()
                    .contentType("application/json")
                    .body(wrongConceptReqDTO)
            .when()
                    .post(BASE_URL)
            .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .contentType(ContentType.JSON)
                    .body("message", res -> equalTo("Field text in DTO is mandatory"))
                    .body("httpStatus", res -> equalTo("BAD_REQUEST"));
        }
    }

    @Nested
    @DisplayName("GET")
    class ConceptGet {
        @Test
        @DisplayName("(FindOne) Should find a Concept with the given id")
        void findOneWhenExists() {
            final ConceptReqDTO conceptReqDTO = new ConceptReqDTO("Software");

            final int id = createConcept(conceptReqDTO).extract().path("id");

            given()
                    .accept(ContentType.JSON)
                    .pathParam("id", id)
            .when()
                    .get(BASE_URL + "{id}")
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("text", res -> equalTo("Software"))
                    .body("_links", res -> hasKey("self"))
                    .body("_links", res -> hasKey("concepts"));
        }

        @Test
        @DisplayName("(FindOne) Should not find a Concept with the given id")
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
        @DisplayName("(FindOne) Should find the created Answer in the Concept")
        void checkThatAnswerIsInConcept(){
            final ConceptReqDTO conceptReqDTO = new ConceptReqDTO("Software");
            final AnswerReqDTO answerReqDTO = new AnswerReqDTO("Software answer", true);

            final int conceptId = createConcept(conceptReqDTO).extract().path("id");
            final int answerId = createAnswer(answerReqDTO,conceptId).extract().path("id");

            given()
                    .accept(ContentType.JSON)
                    .pathParam("conceptId", conceptId)
            .when()
                    .get(BASE_URL + "{conceptId}")
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("answers._embedded.answerList[0].id", res -> is(answerId));
        }

        @Test
        @DisplayName("(FindAll) Should find a Page of Concepts")
        void findAllWhenExists() {
            final ConceptReqDTO conceptReqDTO1 = new ConceptReqDTO("Software");
            final ConceptReqDTO conceptReqDTO2 = new ConceptReqDTO("Hardware");
            final ConceptReqDTO conceptReqDTO3 = new ConceptReqDTO("Functional Programming");
            final ConceptReqDTO conceptReqDTO4 = new ConceptReqDTO("Unix");
            final ConceptReqDTO conceptReqDTO5 = new ConceptReqDTO("Haskell");

            createConcept(conceptReqDTO1);
            createConcept(conceptReqDTO2);
            createConcept(conceptReqDTO3);
            createConcept(conceptReqDTO4);
            createConcept(conceptReqDTO5);

            given()
                    .accept(ContentType.JSON)
            .when()
                    .get(BASE_URL + "?page=0")
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("_embedded", res -> hasKey("conceptRestDTOList"))
                    .body("_embedded.conceptRestDTOList.size()", res -> is(5))
                    .body("_links", res -> hasKey("prev"))
                    .body("_links", res -> hasKey("next"));
        }

        //TODO Create FindAll test when the database is empty (DELETE method needed)
    }

    @Nested
    @DisplayName("PUT")
    class ConceptPut {
        @Test
        @DisplayName("(UpdateOne) Should update the concept")
        void updateWhenExists() {
            final ConceptReqDTO conceptReqDTO1 = new ConceptReqDTO("Software");
            final ConceptReqDTO conceptReqDTO2 = new ConceptReqDTO("Hardware");

            final int id = createConcept(conceptReqDTO1).extract().path("id");

            // Check the initial Concept content
            given()
                    .accept(ContentType.JSON)
                    .pathParam("id", id)
            .when()
                    .get(BASE_URL + "{id}")
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("text", res -> equalTo(conceptReqDTO1.getText()));

            // Update the concept
            given()
                    .contentType("application/json")
                    .pathParam("id", id)
                    .body(conceptReqDTO2)
            .when()
                    .put(BASE_URL + "{id}")
            .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());

            // Check the updated Concept content
            given()
                    .accept(ContentType.JSON)
                    .pathParam("id", id)
            .when()
                    .get(BASE_URL + "{id}")
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("text", res -> equalTo(conceptReqDTO2.getText()));
        }

        @Test
        @DisplayName("(UpdateOne) Should throw an exception")
        void updateWhenNotExists() {
            final ConceptReqDTO conceptReqDTO = new ConceptReqDTO("Software");
            final ConceptReqDTO conceptReqDTO2 = new ConceptReqDTO("Hardware");

            final int id = createConcept(conceptReqDTO).extract().path("id");

            given()
                    .contentType("application/json")
                    .pathParam("id", id + 9999)
                    .body(conceptReqDTO2)
            .when()
                    .put(BASE_URL + "{id}")
            .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
        }
    }

    @Nested
    @DisplayName("DELETE")
    class ConceptDelete {
        @Test
        @DisplayName("(Remove) Should delete the Concept")
        void deleteWhenExits() {
            final ConceptReqDTO conceptReqDTO = new ConceptReqDTO("Software");

            final int id = createConcept(conceptReqDTO).extract().path("id");

            given()
                    .accept(ContentType.JSON)
                    .pathParam("id", id)
            .when()
                    .delete(BASE_URL + "{id}")
            .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());

        }

        @Test
        @DisplayName("(Remove) Should throw an Exception")
        void deleteWhenNotExits() {
            final ConceptReqDTO conceptReqDTO = new ConceptReqDTO("Software");

            final int id = createConcept(conceptReqDTO).extract().path("id");

            given()
                    .accept(ContentType.JSON)
                    .pathParam("id", id + 9854)
            .when()
                    .delete(BASE_URL + "{id}")
            .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());

        }
    }

    /**
     * Create a Concept.
     *
     * @param conceptReqDTO The Concept to be created.
     * @return The response body.
     */
    private ValidatableResponse createConcept(ConceptReqDTO conceptReqDTO) {
        return
                given()
                        .contentType("application/json")
                        .body(conceptReqDTO)
                .when()
                        .post(BASE_URL)
                .then();
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
}
