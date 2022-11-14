package com.example.backend.api.resources.exam.domain.family.question.service;

import com.example.backend.api.resources.exam.domain.family.question.exception.specific.NotEnoughDataException;
import com.example.backend.api.resources.exam.domain.family.question.model.Question;
import com.example.backend.api.resources.exam.domain.family.question.tools.generator.QuestionGenerator;
import com.example.backend.api.resources.exam.domain.factory.TypeFactoryProvider;
import com.example.backend.api.resources.exam.domain.factory.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@Service
public class CreateQuestionService {

    private static final Logger LOG = LoggerFactory.getLogger(CreateQuestionService.class);

    private final List<TypeFactory> typeAbstractFactories;

    public CreateQuestionService(TypeFactoryProvider typeFactoryProvider) {
        this.typeAbstractFactories = typeFactoryProvider.getTypeAbstractFactories();
    }

    public List<Question> createQuestionList(final int numberOfQuestions) {
        int availableQuestions = getNumberOfAvailableNonRepeatedQuestions();
        if (numberOfQuestions > availableQuestions)
            throw new NotEnoughDataException(
                    "An exam with "
                            + numberOfQuestions
                            + " questions cannot be generated due to lack of knowledge, try with an exam with "
                            + availableQuestions
                            + " questions"
            );

        final List<Question> generatedQuestions = new LinkedList<>();

        while (generatedQuestions.size() < numberOfQuestions) {
            final int randomNum = generateRandomNumber();
            QuestionGenerator questionGenerator = typeAbstractFactories.get(randomNum).createGenerator();

            Question question = questionGenerator.generateQuestion(generatedQuestions);
            if (question != null && question.isFilled()) {
                generatedQuestions.add(question);
                LOG.debug("Question created");
            }
        }

        return generatedQuestions;
    }

    private int generateRandomNumber() {
        final int MAX = 3;
        final int MIN = 0;
        return ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
    }

    private int getNumberOfAvailableNonRepeatedQuestions() {
        int availableNumberOfQuestions = 0;
        for (TypeFactory typeFactory : typeAbstractFactories) {
            availableNumberOfQuestions += typeFactory.getNumberOfAvailableQuestions();
        }

        return availableNumberOfQuestions;
    }
}
