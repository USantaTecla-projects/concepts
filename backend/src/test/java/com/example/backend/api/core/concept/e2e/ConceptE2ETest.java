package com.example.backend.api.core.concept.e2e;

import com.example.backend.api.core.concept.dto.ConceptDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.LinkedList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ConceptE2ETest {

    String BASE_URL = "/concepts/";

    ConceptDTO conceptDTO1 = new ConceptDTO("Software", new LinkedList<>());
    ConceptDTO conceptDTO2 = new ConceptDTO("Hardware", new LinkedList<>());
    ConceptDTO conceptDTO3 = new ConceptDTO("Functional Programming", new LinkedList<>());
    ConceptDTO conceptDTO4 = new ConceptDTO("Unix", new LinkedList<>());
    ConceptDTO conceptDTO5 = new ConceptDTO("Haskell", new LinkedList<>());

    ConceptDTO wrongConceptDTO = new ConceptDTO("", new LinkedList<>());

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    @DisplayName("Should create a Concept giving a basic DTO")
    void createWithBasicDTO(){

        given()
                .contentType("application/json")
                .body(conceptDTO1)
        .when()
                .post(BASE_URL)
        .then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.JSON)
                .body("text", res -> equalTo("Software"))
                .body("answers", res -> equalTo(Collections.emptyList()));
    }

    @Test
    @DisplayName("Should not create a Concept giving a wrong DTO")
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

    @Test
    @DisplayName("Should find a Concept with the given id")
    void findOneWithAnExistingId(){
        int id = createConcept(conceptDTO1).extract().path("id");

        given()
                .accept(ContentType.JSON)
                .pathParam("id", id)
        .when()
                .get(BASE_URL+"{id}")
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("text", res -> equalTo("Software"))
                .body("answers", res -> equalTo(Collections.emptyList()))
                .body("_links", res -> hasKey("self"))
                .body("_links", res -> hasKey("concepts"));
    }

    @Test
    @DisplayName("Should not find a Concept with the given id")
    void findOneWithANonExistingId(){
        int id = 999;

        given()
                .accept(ContentType.JSON)
                .pathParam("id", id)
        .when()
                .get(BASE_URL+"{id}")
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Should find a Page of Concepts")
    void findAllWithConceptsInDatabase(){
        createConcept(conceptDTO1);
        createConcept(conceptDTO2);
        createConcept(conceptDTO3);
        createConcept(conceptDTO4);
        createConcept(conceptDTO5);

        given()
                .accept(ContentType.JSON)
        .when()
                .get(BASE_URL+"?page=0")
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("_embedded",res -> hasKey("conceptList"))
                .body("_embedded.conceptList.size()",res -> is(5))
                .body("_links", res -> hasKey("prev"))
                .body("_links", res -> hasKey("next"));
    }

    //TODO Create FindAll test when the database is empty (DELETE method needed)

    private ValidatableResponse createConcept(ConceptDTO conceptDTO) {
        return given()
                .contentType("application/json")
                .body(conceptDTO)
                .when()
                .post(BASE_URL).then();
    }
}
