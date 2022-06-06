package com.example.backend.api.core.concept.e2e;

import com.example.backend.api.core.answer.dto.AnswerDTO;
import com.example.backend.api.core.concept.dto.ConceptDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class AnswerE2ETest {

    int CONCEPT_ID = 1;
    String BASE_URL = "/concepts/" + CONCEPT_ID + "/answers/";


    ConceptDTO conceptDTO1 = new ConceptDTO("Software");

    AnswerDTO answerDTO1 = new AnswerDTO("Software answer", true);
    AnswerDTO conceptDTO2 = new AnswerDTO("Hardware answer", true);
    AnswerDTO conceptDTO3 = new AnswerDTO("Functional Programming answer", false);
    AnswerDTO conceptDTO4 = new AnswerDTO("Unix answer", false);
    AnswerDTO conceptDTO5 = new AnswerDTO("Haskell answer", true);

    AnswerDTO wrongAnswerDTO = new AnswerDTO("");

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @BeforeEach
    void createConceptToWorkWith(){
        CONCEPT_ID = createConcept(conceptDTO1).extract().path("id");
    }

    @Nested
    @DisplayName("POST")
    class AnswerPost {

        @Test
        @DisplayName("(Create) Should create an Answer giving a basic DTO")
        void createWithCorrectDTO() {

            given()
                    .contentType("application/json")
                    .body(answerDTO1)
            .when()
                    .post(BASE_URL)
            .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .contentType(ContentType.JSON)
                    .body("text", res -> equalTo("Software answer"))
                    .body("isCorrect", res -> equalTo(true));
        }

        @Test
        @DisplayName("(Create) Should not create an Answer giving a wrong DTO")
        void createWithWrongDTO() {

            given()
                    .contentType("application/json")
                    .body(wrongAnswerDTO)
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
    class AnswerGet {

        @Test
        @DisplayName("(FindOne) Should find a Concept with the given id")
        void findOneWhenExists() {
            int id = createAnswer(answerDTO1).extract().path("id");

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
    }

    @Nested
    @DisplayName("PUT")
    class AnswerPut {

    }

    @Nested
    @DisplayName("DELETE")
    class AnswerDelete {

    }

    private ValidatableResponse createAnswer(AnswerDTO answerDTO) {
        return
                given()
                        .contentType("application/json")
                        .body(answerDTO)
                .when()
                        .post(BASE_URL).then();
    }

    private ValidatableResponse createConcept(ConceptDTO conceptDTO) {
        return
                given()
                        .contentType("application/json")
                        .body(conceptDTO)
                .when()
                        .post("/concepts/").then();
    }

}
