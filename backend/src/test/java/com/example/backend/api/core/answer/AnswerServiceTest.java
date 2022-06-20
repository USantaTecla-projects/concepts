package com.example.backend.api.core.answer;

import com.example.backend.api.resources.core.answer.AnswerRepository;
import com.example.backend.api.resources.core.answer.dto.AnswerDTO;
import com.example.backend.api.resources.core.answer.exception.model.AnswerDTOBadRequestException;
import com.example.backend.api.resources.core.answer.exception.model.AnswerNotBelongToConceptException;
import com.example.backend.api.resources.core.answer.exception.model.AnswerNotFoundException;
import com.example.backend.api.resources.core.answer.model.Answer;
import com.example.backend.api.resources.core.answer.AnswerService;
import com.example.backend.api.resources.core.concept.ConceptRepository;
import com.example.backend.api.resources.core.concept.model.Concept;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AnswerServiceTest {

    @Mock
    private ConceptRepository conceptRepository;

    @Mock
    private AnswerRepository answerRepository;

    @InjectMocks
    private AnswerService answerService;


    @Nested
    @DisplayName("POST")
    class AnswerPost {

        @Test
        @DisplayName("(Create) Should create an Answer with a correct DTO")
        void createWithCorrectDTO() {
            final AnswerDTO answerDTO = new AnswerDTO("Software answer", true);
            final Answer answer = new Answer(2L, "Software answer", true, 1L);
            final Concept concept = new Concept(1L, "Software", new LinkedList<>(List.of(answer)));

            when(answerRepository.save(any(Answer.class)))
                    .thenReturn(answer);

            Answer createdAnswer = answerService.create(concept, answerDTO);


            // The Answer is created correctly
            assertEquals(answer.getText(), createdAnswer.getText());
            assertEquals(answer.getConceptId(), createdAnswer.getConceptId());

            // The Answer is added to the Concept
            assertEquals(
                    concept.getAnswers().get(0).getText(),
                    answer.getText()
            );
            assertEquals(concept.getAnswers().get(0).getConceptId(), concept.getId());
        }

        @Test
        @DisplayName("(Create) Should not create an Answer with a wrong DTO")
        void createWithWrongDTO() {
            final AnswerDTO wrongAnswerDTO = new AnswerDTO();
            final Answer answer = new Answer(2L, "Software answer", true, 1L);
            final Concept concept = new Concept(1L, "Software", new LinkedList<>(List.of(answer)));

            assertThrows(AnswerDTOBadRequestException.class, () -> answerService.create(concept, wrongAnswerDTO));
        }
    }

    @Nested
    @DisplayName("GET")
    class AnswerGet {

        @Test
        @DisplayName("(FindOne) Should find an Answer with the given id")
        void findOneWhenExists() {
            final Answer answer = new Answer(2L, "Software answer", true, 1L);
            final Concept concept = new Concept(1L, "Software", new LinkedList<>(List.of(answer)));

            when(answerRepository.findById(answer.getId()))
                    .thenReturn(Optional.of(answer));

            Answer foundAnswer = answerService.findOne(concept, answer.getId());
            assertEquals(answer, foundAnswer);
        }

        @Test
        @DisplayName("(FindOne) Should not find an Answer with the given id in the database")
        void findOneWhenNotExists() {
            final Concept concept = new Concept(1L, "Software", new LinkedList<>());
            final long wrongAnswerId = 99L;

            when(answerRepository.findById(wrongAnswerId))
                    .thenReturn(Optional.empty());

            assertThrows(AnswerNotFoundException.class, () -> answerService.findOne(concept, wrongAnswerId));
        }

        @Test
        @DisplayName("(FindOne) Should not find an Answer with the given id on the given Concept")
        void findOneWhenNotBelongToConcept() {
            final Answer answer = new Answer(99L, "Hardware answer", true, 3L);
            final Concept concept = new Concept(1L, "Software", new LinkedList<>());


            when(answerRepository.findById(answer.getId()))
                    .thenReturn(Optional.of(answer));

            assertThrows(AnswerNotBelongToConceptException.class, () -> answerService.findOne(concept, answer.getId()));
        }

        @Test
        @DisplayName("(FindAll) Should find the list of answers in the given Concept")
        void findAllWhenDataExists() {
            final Answer answer = new Answer(2L, "Software answer", true, 1L);
            final Concept concept = new Concept(1L, "Software", new LinkedList<>(List.of(answer)));

            List<Answer> answerList = answerService.findAll(concept);
            assertEquals(answerList.size(), concept.getAnswers().size());
        }

        @Test
        @DisplayName("(FindAll) Should find an empty list of answers in the given Concept")
        void findAllWhenDataNotExists() {
            final Concept concept = new Concept(1L, "Software", new LinkedList<>());

            List<Answer> answerList = answerService.findAll(concept);
            assertEquals(answerList.size(), 0);
        }

    }

    @Nested
    @DisplayName("PUT")
    class AnswerPut {

        @Test
        @DisplayName("(Update) Should update an Answer if exists")
        void updateWhenExists() {
            final Answer answer = new Answer(2L, "Software answer", true, 1L);
            final Concept concept = new Concept(1L, "Software", new LinkedList<>(List.of(answer)));
            final AnswerDTO answerDTO = new AnswerDTO("Software answer", true);

            when(answerRepository.findById(answer.getId()))
                    .thenReturn(Optional.of(answer));

            answerService.updateOne(concept, answer.getId(), answerDTO);
        }

        @Test
        @DisplayName("(Update) Should throw an exception if the Answer is not found")
        void updateWhenNotExists() {
            final Concept concept = new Concept(1L, "Software", new LinkedList<>());
            final AnswerDTO answerDTO = new AnswerDTO("Software answer", true);
            final long wrongAnswerId = 99L;

            assertThrows(AnswerNotFoundException.class, () -> answerService.updateOne(concept, wrongAnswerId, answerDTO));
        }
    }

    @Nested
    @DisplayName("DELETE")
    class AnswerDelete {

        @Test
        @DisplayName("(Delete) Should delete an Answer if exists")
        void deleteWhenExists() {
            final Answer answer = new Answer(2L, "Software answer", true, 1L);
            final Concept concept = new Concept(1L, "Software", new LinkedList<>(List.of(answer)));


            when(answerRepository.findById(answer.getId()))
                    .thenReturn(Optional.of(answer));

            answerService.removeOne(concept, answer.getId());
        }

        @Test
        @DisplayName("(Delete) Should throw an exception if the Answer is not found")
        void deleteWhenNotExists() {
            final Concept concept = new Concept(1L, "Software", new LinkedList<>());
            final long wrongAnswerId = 99L;

            assertThrows(AnswerNotFoundException.class, () -> answerService.removeOne(concept, wrongAnswerId));
        }
    }

}
