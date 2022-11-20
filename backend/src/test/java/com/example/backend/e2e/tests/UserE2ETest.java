package com.example.backend.e2e.tests;

import com.example.backend.e2e.resources.user.dto.UserDTO;
import com.example.backend.e2e.util.UserUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.example.backend.e2e.util.GetAuthToken.getAuthCookie;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserE2ETest {

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
        @DisplayName("Should create a User giving a correct DTO")
        void createWithCorrectDTO() {
            final UserDTO userDTO = new UserDTO(
                    "Cristian de Gracia Nuero",
                    "cris20",
                    "cris@test.com",
                    "1234"
            );

            final Integer userID = RestAssured
                    .given()
                            .cookie("AuthToken", authCookie)
                            .contentType("application/json")
                            .body(userDTO)
                    .when()
                            .post("/users/")
                    .then()
                            .statusCode(HttpStatus.CREATED.value())
                            .contentType(ContentType.JSON)
                            .body("", res -> hasKey("id"))
                            .body("username", res -> equalTo(userDTO.getUsername()))
                            .body("password", res -> not(equalTo(userDTO.getPassword())))
                    .extract()
                            .path("id");

            UserUtils.deleteUser(userID.longValue(), authCookie);
        }

        @Test
        @DisplayName("Should not create a User giving a wrong DTO")
        void createWithWrongDTO() {
            final UserDTO wrongUserDTO = new UserDTO();

            RestAssured
                    .given()
                            .cookie("AuthToken", authCookie)
                            .contentType("application/json")
                            .body(wrongUserDTO)
                    .when()
                            .post("/users/")
                    .then()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .contentType(ContentType.JSON)
                            .body("message", res -> equalTo("Field fullName in User DTO is mandatory"))
                            .body("httpStatus", res -> equalTo("BAD_REQUEST"));
        }
    }

    @Nested
    @DisplayName("GET")
    class UserGet {
        @Test
        @DisplayName("Should find a User with the given ID")
        void findOneWhenExists() {
            final UserDTO userDTO = new UserDTO(
                    "Cristian de Gracia Nuero",
                    "cris20",
                    "cris@test.com",
                    "1234"
            );

            final Long userID = UserUtils.createUser(userDTO, authCookie);

            RestAssured
                    .given()
                            .accept(ContentType.JSON)
                            .pathParam("username", userDTO.getUsername())
                    .when()
                            .get("/users/{username}")
                    .then()
                            .statusCode(HttpStatus.OK.value())
                            .body("username", res -> equalTo(userDTO.getUsername()))
                            .body("", res -> hasKey("id"))
                            .body("", res -> hasKey("fullName"))
                            .body("", res -> hasKey("email"))
                            .body("", res -> hasKey("roles"))
                            .body("", res -> not(hasKey("password")));


            UserUtils.deleteUser(userID, authCookie);
        }

        @Test
        @DisplayName("Should not find a User with the given ID")
        void findOneWhenNotExists() {
            final int id = 999;

            given()
                            .accept(ContentType.JSON)
                            .pathParam("id", id)
                    .when()
                            .get("/users/" + "{id}")
                    .then()
                            .statusCode(HttpStatus.NOT_FOUND.value());
        }


        @Test
        @DisplayName("Should find a Page of Users")
        void findAllWhenExists() {
            final UserDTO userDTO1 = new UserDTO(
                    "Cristian de Gracia Nuero",
                    "cris20",
                    "cris@test.com",
                    "1234"
            );

            final UserDTO userDTO2 = new UserDTO(
                    "Marcos de Gracia Nuero",
                    "mar20",
                    "mar@test.com",
                    "1234"
            );


            final Long userID1 = UserUtils.createUser(userDTO1, authCookie);
            final Long userID2 = UserUtils.createUser(userDTO2, authCookie);

            RestAssured
                    .given()
                            .accept(ContentType.JSON)
                    .when()
                            .get("/users/" + "?page=0")
                    .then()
                            .statusCode(HttpStatus.OK.value())
                            .body("", res -> hasKey("content"))
                            .body("content.size()", res -> lessThanOrEqualTo(5))
                            .body("", res -> hasKey("pageable"))
                            .body("", res -> hasKey("totalPages"));

            UserUtils.deleteUser(userID1, authCookie);
            UserUtils.deleteUser(userID2, authCookie);
        }
    }

    @Nested
    @DisplayName("PUT")
    class UserPut {
        @Test
        @DisplayName("Should update the User")
        void updateWhenExists() {
            final UserDTO userDTO1 = new UserDTO(
                    "Cristian de Gracia Nuero",
                    "cris20",
                    "cris@test.com",
                    "1234"
            );

            final UserDTO userDTO2 = new UserDTO(
                    "Marcos de Gracia Nuero",
                    "mar20",
                    "mar@test.com",
                    "1234"
            );


            final Long userID = UserUtils.createUser(userDTO1, authCookie);

            RestAssured
                    .given()
                            .accept(ContentType.JSON)
                            .pathParam("username", userDTO1.getUsername())
                    .when()
                            .get("/users/{username}")
                    .then()
                            .statusCode(HttpStatus.OK.value())
                            .body("username", res -> equalTo(userDTO1.getUsername()));

            RestAssured
                    .given()
                            .contentType("application/json")
                            .pathParam("userID", userID)
                            .body(userDTO2)
                    .when()
                            .cookie("AuthToken", authCookie)
                            .put("/users/{userID}")
                    .then()
                            .statusCode(HttpStatus.NO_CONTENT.value());
            RestAssured
                    .given()
                            .accept(ContentType.JSON)
                            .pathParam("username", userDTO2.getUsername())
                    .when()
                            .get("/users/{username}")
                    .then()
                            .statusCode(HttpStatus.OK.value())
                            .body("username", res -> equalTo(userDTO2.getUsername()))
                            .body("id", res -> equalTo(userID.intValue()));

            UserUtils.deleteUser(userID, authCookie);
        }

        @Test
        @DisplayName("Should throw an Exception on updating a non-existing User")
        void updateWhenNotExists() {
            final UserDTO userDTO = new UserDTO(
                    "Marcos de Gracia Nuero",
                    "mar20",
                    "mar@test.com",
                    "1234"
            );

            RestAssured
                    .given()
                            .cookie("AuthToken", authCookie)
                            .contentType("application/json")
                            .pathParam("userID", 9999)
                            .body(userDTO)
                    .when()
                            .put("/users/{userID}")
                    .then()
                            .statusCode(HttpStatus.NOT_FOUND.value());
        }
    }

    @Nested
    @DisplayName("DELETE")
    class UserDelete {
        @Test
        @DisplayName("Should delete the User")
        void deleteWhenExits() {
            final UserDTO userDTO = new UserDTO(
                    "Cristian de Gracia Nuero",
                    "cris20",
                    "cris@test.com",
                    "1234"
            );


            final Long userID = UserUtils.createUser(userDTO, authCookie);

            RestAssured
                    .given()
                            .cookie("AuthToken", authCookie)
                            .accept(ContentType.JSON)
                            .pathParam("userID", userID)
                    .when()
                            .delete("/users/{userID}")
                    .then()
                            .statusCode(HttpStatus.NO_CONTENT.value());

        }

        @Test
        @DisplayName("Should throw an Exception on deleting a non-existing User")
        void deleteWhenNotExits() {

            RestAssured
                    .given()
                            .cookie("AuthToken", authCookie)
                            .accept(ContentType.JSON)
                            .pathParam("userID", 999)
                    .when()
                            .delete("/users/" + "{userID}")
                    .then()
                            .statusCode(HttpStatus.NOT_FOUND.value());
        }
    }
}
