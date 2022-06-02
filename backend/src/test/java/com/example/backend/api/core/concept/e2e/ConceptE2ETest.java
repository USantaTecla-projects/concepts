package com.example.backend.api.core.concept.e2e;

import com.example.backend.api.core.concept.dto.ConceptDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.LinkedList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ConceptE2ETest {

    String BASE_URL = "/concepts/";

    ConceptDTO basicConceptDTO = new ConceptDTO("Software", new LinkedList<>());

    ConceptDTO wrongConceptDTO = new ConceptDTO("", new LinkedList<>());

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    @DisplayName("should create a Concept giving a basic DTO")
    void createWithBasicDTO(){

        given()
                .contentType("application/json")
                .body(basicConceptDTO)
        .when()
                .post(BASE_URL)
        .then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.JSON)
                .body("text", res -> equalTo("Software"))
                .body("answers", res -> equalTo(Collections.emptyList()));
    }

    @Test
    @DisplayName("should not create a Concept giving a wrong DTO")
    void createWithWrongDTO(){
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
