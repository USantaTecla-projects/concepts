package com.example.backend.api.core.test.service;

import com.example.backend.api.core.answer.AnswerRepository;
import com.example.backend.api.core.answer.exception.model.AnswerDTOBadRequestException;
import com.example.backend.api.core.answer.exception.model.AnswerNotBelongToConceptException;
import com.example.backend.api.core.answer.exception.model.AnswerNotFoundException;
import com.example.backend.api.core.answer.model.Answer;
import com.example.backend.api.core.answer.service.AnswerService;
import com.example.backend.api.core.concept.ConceptRepository;
import com.example.backend.api.core.concept.exception.model.ConceptNotFoundException;
import com.example.backend.api.core.concept.model.Concept;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.util.List;
import java.util.Optional;

import static com.example.backend.api.core.data.AnswerTestDataConstants.*;
import static com.example.backend.api.core.data.ConceptTestDataConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class AnswerServiceTest {

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private ConceptRepository conceptRepository;

    @InjectMocks
    private AnswerService answerService;



    @BeforeEach
    void setUpAnswerRepositoryMocks() {
        lenient().when(answerRepository.save(any(Answer.class))).thenReturn(answer1);

        lenient().when(answerRepository.findById(2L)).thenReturn(Optional.of(answer1));
        lenient().when(answerRepository.findById(4L)).thenReturn(Optional.empty());

    }

    @BeforeEach
    void setUpConceptRepositoryMocks() {
        lenient().when(conceptRepository.save(concept1Answer1)).thenReturn(concept1Answer1);
    }

    @Nested
    @DisplayName("POST")
    class AnswerPost {

        @Test
        @DisplayName("(Create) Should create an Answer with a correct DTO")
        void createWithCorrectDTO() {
            Answer createdAnswer = answerService.create(concept1,answerDTO1);

            // The Answer is created correctly
            assertEquals(answer1.getText(), createdAnswer.getText());
            assertEquals(answer1.getConceptId(), createdAnswer.getConceptId());

            // The Answer is added to the Concept
            assertEquals(
                    concept1.getAnswers().get(0).getText(),
                    concept1Answer1.getAnswers().get(0).getText()
            );
            assertEquals(concept1.getAnswers().get(0).getConceptId(),concept1.getId());
        }

        @Test
        @DisplayName("(Create) Should not create an Answer with a wrong DTO")
        void createWithWrongDTO(){
            assertThrows(AnswerDTOBadRequestException.class, () -> answerService.create(concept1,wrongAnswerDTO1));
            assertThrows(AnswerDTOBadRequestException.class, () -> answerService.create(concept1,wrongAnswerDTO2));
        }
    }

    @Nested
    @DisplayName("GET")
    class AnswerGet{

        @Test
        @DisplayName("(FindOne) Should find an Answer with the given id")
        void findOneWhenExists() {
            Answer answer = answerService.findOne(concept1Answer1,2L);
            assertEquals(answer, answer1);
        }

        @Test
        @DisplayName("(FindOne) Should not find an Answer with the given id")
        void findOneWhenNotExists() {
            assertThrows(AnswerNotFoundException.class, () -> answerService.findOne(concept1Answer1,4L));
        }

        @Test
        @DisplayName("(FindOne) Should not find an Answer with the given id on the given Concept")
        void findOneWhenNotBelongToConcept() {
            assertThrows(AnswerNotBelongToConceptException.class, () -> answerService.findOne(concept2Answer2,2L));
        }

        @Test
        @DisplayName("(FindAll) Should find the list of answers in the given Concept")
        void findAllWhenDataExists() {
            List<Answer> answerList = answerService.findAll(concept1Answer1);
            assertEquals(answerList.size(), 1);
        }

        @Test
        @DisplayName("(FindAll) Should find an empty list of answers in the given Concept")
        void findAllWhenDataNotExists() {
            List<Answer> answerList = answerService.findAll(concept1);
            assertEquals(answerList.size(), 0);
        }

    }

    @Nested
    @DisplayName("PUT")
    class AnswerPut{

        @Test
        @DisplayName("(Update) Should update an Answer if exists")
        void updateWhenExists() {
            answerService.updateOne(concept1Answer1,2L, answerDTO1);
        }

        @Test
        @DisplayName("(Update) Should throw an exception if the Answer is not found")
        void updateWhenNotExists() {
            assertThrows(AnswerNotFoundException.class, () -> answerService.updateOne(concept1Answer1,1L, answerDTO1));
        }
    }

    @Nested
    @DisplayName("DELETE")
    class AnswerDelete{

        @Test
        @DisplayName("(Delete) Should delete an Answer if exists")
        void deleteWhenExists() {
            answerService.removeOne(concept1Answer1,2L);
        }

        @Test
        @DisplayName("(Delete) Should throw an exception if the Answer is not found")
        void deleteWhenNotExists() {
            assertThrows(AnswerNotFoundException.class, () -> answerService.removeOne(concept1Answer1,4L));
        }
    }

}
