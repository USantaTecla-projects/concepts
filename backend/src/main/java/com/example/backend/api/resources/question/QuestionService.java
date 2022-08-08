package com.example.backend.api.resources.question;

import com.example.backend.api.resources.question.dto.GetQuestionsDTO;
import com.example.backend.api.resources.question.exception.model.NotEnoughDataException;
import com.example.backend.api.resources.question.exception.model.QuestionDTOBadRequestException;
import com.example.backend.api.resources.question.filler.Filler;
import com.example.backend.api.resources.question.models.Question;
import com.example.backend.api.resources.question.type.factory.TypeFactoryProvider;
import com.example.backend.api.resources.question.type.factory.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@Service
public class QuestionService {

    private static final Logger LOG = LoggerFactory.getLogger(QuestionService.class);

    private final TypeFactoryProvider typeFactoryProvider;
    private final List<TypeFactory> typeAbstractFactories;

    public QuestionService(TypeFactoryProvider typeFactoryProvider) {
        this.typeFactoryProvider = typeFactoryProvider;
        this.typeAbstractFactories = typeFactoryProvider.getTypeAbstractFactory();
    }

    /**
     * Create an exam by a given DTO (if it is correct).
     *
     * @param getQuestionsDTO The data object to create the exam.
     * @return The created exam.
     * @author Cristian
     */
    public List<Question> create(final GetQuestionsDTO getQuestionsDTO) {
        final int numberOfQuestions = getQuestionsDTO
                .getNumberOfQuestionsOptional(getQuestionsDTO.getNumberOfQuestions())
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

        while (questions.size() < numberOfQuestions) {
            final int randomNum = generateRandomNumber();
            Question question = typeAbstractFactories.get(randomNum).createQuestion();
            Filler filler = typeAbstractFactories.get(randomNum).createFiller();

            try {
                question.accept(filler, questions);
                if (question.isFilled())
                    questions.add(question);
            } catch (Exception exception) {
                LOG.error(exception.getMessage());
            }


        }

        return questions;
    }

    private int generateRandomNumber() {
        final int MAX = 3;
        final int MIN = 0;
        return ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
    }


    private int getNumberOfAvailableNonRepeatedQuestions() {
        int availableNumberOfQuestions = 0;
        for (TypeFactory typeFactory : typeAbstractFactories) {
            availableNumberOfQuestions += typeFactory.createNumberOfAvailableQuestions().numberOfAvailableQuestions();
        }

        return availableNumberOfQuestions;
    }
}
