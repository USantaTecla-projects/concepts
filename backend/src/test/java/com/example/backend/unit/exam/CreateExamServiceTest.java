package com.example.backend.unit.exam;

import com.example.backend.api.resources.exam.domain.factory.Type;
import com.example.backend.api.resources.exam.domain.family.question.model.specific.QuestionT0;
import com.example.backend.api.resources.exam.domain.family.question.service.CreateQuestionService;
import com.example.backend.api.resources.exam.dto.CreateExamDTO;
import com.example.backend.api.resources.exam.exception.specific.UpdateExamDTOBadRequestException;
import com.example.backend.api.resources.exam.model.Exam;
import com.example.backend.api.resources.exam.service.CreateExamService;
import com.example.backend.api.resources.knowledge.concept.model.Concept;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateExamServiceTest {

    @Mock
    private CreateQuestionService createQuestionService;

    @InjectMocks
    private CreateExamService createExamService;

    @Test
    @DisplayName("Should create an Exam with a correct DTO")
    void createExamWithCorrectDTO() {
        final Concept concept1 = new Concept(1L, "Software", new LinkedList<>());
        final Concept concept2 = new Concept(2L, "Hardware", new LinkedList<>());

        final CreateExamDTO createExamDTO = new CreateExamDTO(2, 3L);

        when(createQuestionService.createQuestionList(createExamDTO.getNumberOfQuestions()))
                .thenReturn(
                        List.of(
                                new QuestionT0(Type.TYPE0, concept1.getId(), concept1.getText()),
                                new QuestionT0(Type.TYPE0, concept2.getId(), concept2.getText())
                        )
                );

        Exam exam = createExamService.create(createExamDTO);

        assert exam.getQuestionList().size() == 2;
        assert exam.getUserID() == 3L;
    }

    @Test
    @DisplayName("Should not create an Exam with an incorrect DTO")
    void createExamWithIncorrectDTO() {
        final CreateExamDTO createExamDTO = new CreateExamDTO();
        assertThrows(UpdateExamDTOBadRequestException.class, () -> createExamService.create(createExamDTO));
    }
}
