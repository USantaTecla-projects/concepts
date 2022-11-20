package com.example.backend.e2e.util;

import com.example.backend.e2e.resources.knowledge.concept.dto.ConceptDTO;
import com.example.backend.e2e.resources.knowledge.definition.dto.DefinitionDTO;
import com.example.backend.e2e.resources.knowledge.justification.dto.JustificationDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class KnowledgeUtils {

    public static Long createConcept(final ConceptDTO conceptDTO, String authCookie) {
        Integer conceptID = RestAssured
                .given()
                        .cookie("AuthToken", authCookie)
                        .contentType("application/json").body(conceptDTO)
                .when()
                        .post("/concepts/")
                .then()
                    .statusCode(HttpStatus.CREATED.value())
                .extract()
                    .path("id");

        return conceptID.longValue();
    }

    public static void deleteConcept(final Long conceptID, String authCookie) {
        RestAssured
                .given()
                        .cookie("AuthToken", authCookie)
                        .accept(ContentType.JSON)
                        .pathParam("conceptID", conceptID)
                .when()
                        .delete("/concepts/{conceptID}")
                .then()
                        .statusCode(HttpStatus.NO_CONTENT.value());
    }


    public static Long createDefinition(final DefinitionDTO definitionDTO, final Long conceptID, final String authCookie) {
        Integer definitionID = RestAssured
                .given()
                    .cookie("AuthToken", authCookie)
                    .contentType("application/json")
                    .pathParam("conceptID", conceptID)
                    .body(definitionDTO)
                .when()
                    .post("/concepts/{conceptID}/definitions/")
                .then()
                    .statusCode(HttpStatus.CREATED.value())
                .extract()
                    .path("id");

        return definitionID.longValue();

    }

    public static void deleteDefinition(final Long conceptID, final Long definitionID, String authCookie) {
        RestAssured
                .given()
                        .cookie("AuthToken", authCookie)
                        .accept(ContentType.JSON)
                        .pathParam("conceptID", conceptID)
                        .pathParam("definitionID", definitionID)
                .when()
                        .delete("/concepts/{conceptID}/definitions/{definitionID}")
                .then()
                        .statusCode(HttpStatus.NO_CONTENT.value());
    }

    public static Long createJustification(
            final JustificationDTO justificationDTO,
            final Long conceptID,
            final Long definitionID,
            final String authCookie
    ) {
        Integer justificationID = RestAssured
                .given()
                    .cookie("AuthToken", authCookie)
                    .contentType("application/json")
                    .pathParam("conceptID", conceptID)
                    .pathParam("definitionID", definitionID)
                    .body(justificationDTO)
                .when()
                    .post("/concepts/{conceptID}/definitions/{definitionID}/justifications/")
                .then()
                    .statusCode(HttpStatus.CREATED.value())
                .extract()
                    .path("id");

        return justificationID.longValue();

    }

    public static void deleteJustification(
            final Long conceptID,
            final Long definitionID,
            final Long justificationID,
            String authCookie
    ) {
        RestAssured
                .given()
                        .cookie("AuthToken", authCookie)
                        .accept(ContentType.JSON)
                        .pathParam("conceptID", conceptID)
                        .pathParam("definitionID", definitionID)
                        .pathParam("justificationID", justificationID)
                .when()
                        .delete("/concepts/{conceptID}/definitions/{definitionID}/justifications/{justificationID}")
                .then()
                        .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
