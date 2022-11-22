package com.example.backend.e2e.tests;

import com.example.backend.api.resources.exam.dto.CreateExamDTO;
import com.example.backend.api.resources.knowledge.concept.dto.ConceptDTO;
import com.example.backend.api.resources.knowledge.definition.dto.DefinitionDTO;
import com.example.backend.api.resources.knowledge.justification.dto.JustificationDTO;
import com.example.backend.api.resources.user.dto.UserDTO;
import com.example.backend.e2e.util.KnowledgeUtils;
import com.example.backend.e2e.util.UserUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.example.backend.e2e.util.GetAuthToken.getAuthCookie;
import static org.hamcrest.Matchers.*;

public class ExamE2ETest {

    private static String authCookie;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://localhost";
        RestAssured.port = 8443;
        RestAssured.useRelaxedHTTPSValidation();

        authCookie = getAuthCookie();
    }


    @Test
    @DisplayName("Should create an Exam with a correct DTO")
    void createWithCorrectDTO() {
        final UserDTO userDTO = new UserDTO(
                "Cristian de Gracia Nuero",
                "cris20",
                "cris@test.com",
                "1234"
        );

        final Long userID = UserUtils.createUser(userDTO, authCookie);

        final ConceptDTO conceptDTO = new ConceptDTO("Software");
        final DefinitionDTO definitionDTO = new DefinitionDTO("Software definition", true);
        final JustificationDTO justificationDTO = new JustificationDTO("Software justification", true, null);

        final Long conceptID = KnowledgeUtils.createConcept(conceptDTO, authCookie);
        final Long definitionID = KnowledgeUtils.createDefinition(definitionDTO, conceptID, authCookie);
        final Long justificationID = KnowledgeUtils.createJustification(justificationDTO, conceptID, definitionID, authCookie);

        final CreateExamDTO createExamDTO = new CreateExamDTO(4, userID);

        RestAssured
                 .given()
                        .cookie("AuthToken", authCookie)
                        .contentType("application/json")
                        .pathParam("userID", userID)
                        .body(createExamDTO)
                .when()
                        .post("/{userID}/exams/")
                .then()
                        .statusCode(HttpStatus.CREATED.value())
                        .contentType(ContentType.JSON)
                        .body("userID", res -> equalTo(userID.intValue()))
                        .body("", hasKey("creationDate"))
                        .body("corrected", res -> equalTo(false))
                        .body("questionList.size()", res -> equalTo(4));

        KnowledgeUtils.deleteJustification(conceptID, definitionID, justificationID, authCookie);
        KnowledgeUtils.deleteDefinition(conceptID, definitionID, authCookie);
        KnowledgeUtils.deleteConcept(conceptID, authCookie);

        UserUtils.deleteUser(userID, authCookie);
    }

    @Test
    @DisplayName("Should not create an Exam with an incorrect DTO")
    void createWithWrongDTO() {
        final UserDTO userDTO = new UserDTO(
                "Cristian de Gracia Nuero",
                "cris20",
                "cris@test.com",
                "1234"
        );

        final Long userID = UserUtils.createUser(userDTO, authCookie);

        final CreateExamDTO createExamDTO = new CreateExamDTO();

        RestAssured
                 .given()
                        .cookie("AuthToken", authCookie)
                        .contentType("application/json")
                        .pathParam("userID", userID)
                        .body(createExamDTO)
                .when()
                        .post("/{userID}/exams/")
                .then()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .contentType(ContentType.JSON)
                        .body("message", res -> equalTo("Field userID in CreateExam DTO is mandatory"))
                        .body("httpStatus", res -> equalTo("BAD_REQUEST"));

        UserUtils.deleteUser(userID, authCookie);
    }

    @Test
    @DisplayName("Should not create an Exam without enough knowledge")
    void createWithoutEnoughKnowledge() {
        final UserDTO userDTO = new UserDTO(
                "Cristian de Gracia Nuero",
                "cris20",
                "cris@test.com",
                "1234"
        );

        final Long userID = UserUtils.createUser(userDTO, authCookie);

        final ConceptDTO conceptDTO = new ConceptDTO("Software");
        final Long conceptID = KnowledgeUtils.createConcept(conceptDTO, authCookie);

        final CreateExamDTO createExamDTO = new CreateExamDTO(20, userID);

        RestAssured
                 .given()
                        .cookie("AuthToken", authCookie)
                        .contentType("application/json")
                        .pathParam("userID", userID)
                        .body(createExamDTO)
                .when()
                        .post("/{userID}/exams/")
                .then()
                        .statusCode(HttpStatus.FAILED_DEPENDENCY.value())
                        .contentType(ContentType.JSON)
                        .body("message", res -> containsString("An exam with 20 questions cannot be generated due to lack of knowledge"))
                        .body("httpStatus", res -> equalTo("FAILED_DEPENDENCY"));

        KnowledgeUtils.deleteConcept(conceptID, authCookie);
        UserUtils.deleteUser(userID, authCookie);
    }
}
