package com.example.backend.e2e.util;

import com.example.backend.e2e.resources.user.dto.UserDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class UserUtils {

    public static Long createUser(UserDTO userDTO, String authCookie) {
        final Integer userID = RestAssured
                .given()
                        .cookie("AuthToken", authCookie)
                        .contentType("application/json")
                        .body(userDTO)
                .when()
                        .post("/users/")
                .then()
                        .statusCode(HttpStatus.CREATED.value())
                .extract()
                        .path("id");

        return userID.longValue();
    }

    public static void deleteUser(Long userID, String authCookie) {
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
}
