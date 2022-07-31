package com.example.backend.api.resources.question;

import com.example.backend.api.resources.knowledge.answer.AnswerRepository;
import com.example.backend.api.resources.knowledge.concept.ConceptRepository;
import com.example.backend.api.resources.knowledge.justification.JustificationRepository;
import com.example.backend.api.resources.question.dto.CreateExamDTO;
import com.example.backend.api.resources.question.exception.model.NotEnoughDataException;
import com.example.backend.api.resources.question.exception.model.QuestionDTOBadRequestException;
import com.example.backend.api.resources.question.models.*;
import com.example.backend.api.resources.question.models.specific.QuestionT0;
import com.example.backend.api.resources.question.models.specific.QuestionT1;
import com.example.backend.api.resources.question.models.specific.QuestionT2;
import com.example.backend.api.resources.question.models.specific.QuestionT3;
import com.example.backend.api.resources.question.visitor.Visitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;


@Service
public class QuestionService {

    private static final Logger LOG = LoggerFactory.getLogger(QuestionService.class);
    public final ConceptRepository conceptRepository;
    public final AnswerRepository answerRepository;
    public final JustificationRepository justificationRepository;
    private final Visitor visitor;

    public QuestionService(
            ConceptRepository conceptRepository,
            AnswerRepository answerRepository,
            JustificationRepository justificationRepository,
            Visitor visitor
    ) {
        this.conceptRepository = conceptRepository;
        this.answerRepository = answerRepository;
        this.justificationRepository = justificationRepository;
        this.visitor = visitor;
    }

    /**
     * Create an exam by a given DTO (if it is correct).
     *
     * @param createExamDTO The data object to create the exam.
     * @return The created exam.
     * @author Cristian
     */
    public List<Question> create(final CreateExamDTO createExamDTO) {
        final int numberOfQuestions = createExamDTO
                .getNumberOfQuestionsOptional(createExamDTO.getNumberOfQuestions())
                .orElseThrow(() -> new QuestionDTOBadRequestException("Field numberOfQuestions in CreateExam DTO is mandatory"));

        int availableQuestions = getNumberOfAvailableNonRepeatedQuestions();
        if (numberOfQuestions > availableQuestions)
            throw new NotEnoughDataException(
                    "An exam with "
                            + numberOfQuestions
                            + " questions cannot be generated due to lack of knowledge, try with an exam with "
                            + availableQuestions
                            + " questions"
            );

        final List<Question> questions = new LinkedList<>();
        final Map<QuestionType, List<Question>> questionReferences = initQuestionReferences();
        final int MAX = 3;
        final int MIN = 0;
        int randomNum;

        while (questions.size() < numberOfQuestions) {
            randomNum = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
            Question question = createQuestion(randomNum);

            try {
                question.accept(visitor, questionReferences);
                questions.add(question);
                questionReferences.get(question.getType()).add(question);
            } catch (Exception exception) {
                LOG.error(exception.getMessage());
            }


        }

        System.out.println(questionReferences);

        return questions;
    }

    /**
     * Create a question of a specific type based on a number.
     *
     * @param questionType The type of question that is wanted.
     * @return A question specific instance.
     * @author Cristian
     */
    private Question createQuestion(int questionType) {
        return switch (questionType) {
            case 0 -> new QuestionT0();
            case 1 -> new QuestionT1();
            case 2 -> new QuestionT2();
            case 3 -> new QuestionT3();
            default -> null;
        };
    }

    private int getNumberOfAvailableNonRepeatedQuestions() {
        long type0 = conceptRepository.countAvailableType0Questions();
        long type1 = answerRepository.countAvailableType1Questions();
        long type2 = answerRepository.countAvailableType2Questions();
        long type3 = justificationRepository.countAvailableType3Questions();
        return Stream.of(type0, type1, type2, type3).reduce(0L, Long::sum).intValue();
    }

    private Map<QuestionType, List<Question>> initQuestionReferences() {
        Map<QuestionType, List<Question>> questionReferences = new HashMap<>();
        List<QuestionType> questionTypes = List.of(
                QuestionType.TYPE0,
                QuestionType.TYPE1,
                QuestionType.TYPE2,
                QuestionType.TYPE3
        );
        questionTypes.forEach(type -> questionReferences.put(type, new LinkedList<>()));
        return questionReferences;
    }
}
