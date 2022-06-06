package com.example.backend.api.core.test.e2e;

import com.example.backend.api.core.answer.dto.AnswerDTO;
import com.example.backend.api.core.concept.dto.ConceptDTO;
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

    String BASE_URL = "/concepts/";

    ConceptDTO conceptDTO1 = new ConceptDTO("Software");
    ConceptDTO conceptDTO2 = new ConceptDTO("Hardware");
    ConceptDTO conceptDTO3 = new ConceptDTO("Functional Programming");
    ConceptDTO conceptDTO4 = new ConceptDTO("Unix");
    ConceptDTO conceptDTO5 = new ConceptDTO("Haskell");

    ConceptDTO wrongConceptDTO = new ConceptDTO("");

    AnswerDTO answerDTO1 = new AnswerDTO("Software answer", true);

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

            given()
                    .contentType("application/json")
                    .body(conceptDTO1)
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
            given()
                    .contentType("application/json")
                    .body(wrongConceptDTO)
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
            int id = createConcept(conceptDTO1).extract().path("id");

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
            int id = 999;

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
            int conceptId = createConcept(conceptDTO1).extract().path("id");
            int answerId = createAnswer(answerDTO1,conceptId).extract().path("id");

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
            createConcept(conceptDTO1);
            createConcept(conceptDTO2);
            createConcept(conceptDTO3);
            createConcept(conceptDTO4);
            createConcept(conceptDTO5);

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
            int id = createConcept(conceptDTO1).extract().path("id");

            // Check the initial Concept content
            given()
                    .accept(ContentType.JSON)
                    .pathParam("id", id)
            .when()
                    .get(BASE_URL + "{id}")
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("text", res -> equalTo(conceptDTO1.getText()));

            // Update the concept
            given()
                    .contentType("application/json")
                    .pathParam("id", id)
                    .body(conceptDTO2)
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
                    .body("text", res -> equalTo(conceptDTO2.getText()));
        }

        @Test
        @DisplayName("(UpdateOne) Should throw an exception")
        void updateWhenNotExists() {
            int id = createConcept(conceptDTO1).extract().path("id");

            given()
                    .contentType("application/json")
                    .pathParam("id", id + 9999)
                    .body(conceptDTO2)
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
            int id = createConcept(conceptDTO1).extract().path("id");

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
            int id = createConcept(conceptDTO1).extract().path("id");

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
     * @param conceptDTO The Concept to be created.
     * @return The response body.
     */
    private ValidatableResponse createConcept(ConceptDTO conceptDTO) {
        return
                given()
                        .contentType("application/json")
                        .body(conceptDTO)
                .when()
                        .post(BASE_URL)
                .then();
    }

    /**
     * Create an Answer in a Concept by its id.
     *
     * @param answerDTO The Answer to be created.
     * @param conceptId The Concept id where the Answer should be created.
     * @return The response body.
     */
    private ValidatableResponse createAnswer(AnswerDTO answerDTO,int conceptId) {
        return
                given()
                        .contentType("application/json")
                        .body(answerDTO)
                .when()
                        .post("/concepts/" + conceptId + "/answers/")
                .then();
    }
}
