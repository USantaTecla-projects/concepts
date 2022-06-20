package com.example.backend.api.user;

import com.example.backend.api.resources.user.dto.UserDTO;
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
import static org.hamcrest.Matchers.equalTo;

public class UserE2ETest {

    final String BASE_URL = "/users/";
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
    class UserPost {
        @Test
        @DisplayName("(Create) Should create a user giving a basic DTO")
        void createWithCorrectDTO() {
            final UserDTO userDTO = new UserDTO("Cristian","1234");

            given()
                    .cookie("AuthToken",authCookie)
                    .contentType("application/json")
                    .body(userDTO)
            .when()
                    .post(BASE_URL)
            .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .contentType(ContentType.JSON)
                    .body("", res -> hasKey("id"))
                    .body("username", res -> equalTo(userDTO.getUsername()))
                    .body("password", res -> not(equalTo(userDTO.getPassword())));

        }

        @Test
        @DisplayName("(Create) Should not create a user giving a wrong DTO")
        void createWithWrongDTO() {
            final UserDTO wrongUserDTO = new UserDTO();

            given()
                    .cookie("AuthToken",authCookie)
                    .contentType("application/json")
                    .body(wrongUserDTO)
            .when()
                    .post(BASE_URL)
            .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .contentType(ContentType.JSON)
                    .body("message", res -> equalTo("Field username in User DTO is mandatory"))
                    .body("httpStatus", res -> equalTo("BAD_REQUEST"));
        }
    }

    @Nested
    @DisplayName("GET")
    class UserGet {
        @Test
        @DisplayName("(FindOne) Should find a user with the given id")
        void findOneWhenExists() {
            final UserDTO userDTO = new UserDTO("Cristian","1234");


            final int id = createUser(userDTO).extract().path("id");

            given()
                    .accept(ContentType.JSON)
                    .pathParam("id", id)
            .when()
                    .get(BASE_URL + "{id}")
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("", res -> hasKey("id"))
                    .body("username", res -> equalTo(userDTO.getUsername()))
                    .body("", res -> hasKey("password"));
        }

        @Test
        @DisplayName("(FindOne) Should not find a user with the given id")
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
        @DisplayName("(FindAll) Should find a page of users")
        void findAllWhenExists() {
            final UserDTO userDTO1 = new UserDTO("Cristian","1234");
            final UserDTO userDTO2 = new UserDTO("Marcos","5678");

            createUser(userDTO1);
            createUser(userDTO2);

            given()
                    .accept(ContentType.JSON)
            .when()
                    .get(BASE_URL + "?page=0")
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("", res -> hasKey("content"))
                    .body("content.size()", res -> lessThanOrEqualTo(5))
                    .body("", res -> hasKey("pageable"))
                    .body("", res -> hasKey("totalPages"));
        }

    }

    @Nested
    @DisplayName("PUT")
    class UserPut {
        @Test
        @DisplayName("(UpdateOne) Should update the user")
        void updateWhenExists() {
            final UserDTO userDTO1 = new UserDTO("Cristian","1234");
            final UserDTO userDTO2 = new UserDTO("Marcos","5678");

            final int id = createUser(userDTO1).extract().path("id");

            // Check the initial user content
            given()
                    .accept(ContentType.JSON)
                    .pathParam("id", id)
            .when()
                    .get(BASE_URL + "{id}")
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("username", res -> equalTo(userDTO1.getUsername()));

            // Update the concept
            given()
                    .contentType("application/json")
                    .pathParam("id", id)
                    .body(userDTO2)
            .when()
                    .cookie("AuthToken",authCookie)
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
                    .body("username", res -> equalTo(userDTO2.getUsername()));
        }

        @Test
        @DisplayName("(UpdateOne) Should throw an exception")
        void updateWhenNotExists() {
            final UserDTO userDTO1 = new UserDTO("Cristian","1234");
            final UserDTO userDTO2 = new UserDTO("Marcos","5678");

            final int id = createUser(userDTO1).extract().path("id");

            given()
                    .cookie("AuthToken",authCookie)
                    .contentType("application/json")
                    .pathParam("id", id + 9999)
                    .body(userDTO2)
            .when()
                    .put(BASE_URL + "{id}")
            .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
        }
    }

    @Nested
    @DisplayName("DELETE")
    class UserDelete {
        @Test
        @DisplayName("(Remove) Should delete the Concept")
        void deleteWhenExits() {
            final UserDTO userDTO = new UserDTO("Cristian","1234");

            final int id = createUser(userDTO).extract().path("id");

            given()
                    .cookie("AuthToken",authCookie)
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
            final UserDTO userDTO = new UserDTO("Cristian","1234");

            final int id = createUser(userDTO).extract().path("id");

            given()
                    .cookie("AuthToken",authCookie)
                    .accept(ContentType.JSON)
                    .pathParam("id", id + 9854)
            .when()
                    .delete(BASE_URL + "{id}")
            .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());

        }
    }

    /**
     * Create a user.
     *
     * @param userDTO The user to be created.
     * @return The response body.
     */
    private ValidatableResponse createUser(UserDTO userDTO) {
        return
                given()
                        .cookie("AuthToken",authCookie)
                        .contentType("application/json")
                        .body(userDTO)
                .when()
                        .post(BASE_URL)
                .then();
    }
}
