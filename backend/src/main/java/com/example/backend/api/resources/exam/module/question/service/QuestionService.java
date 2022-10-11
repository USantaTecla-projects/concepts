package com.example.backend.api.resources.exam.module.question.service;

import com.example.backend.api.resources.exam.module.question.dto.QuestionDTO;
import com.example.backend.api.resources.exam.module.question.exception.specific.NotEnoughDataException;
import com.example.backend.api.resources.exam.module.question.exception.specific.QuestionDTOBadRequestException;
import com.example.backend.api.resources.exam.module.question.mapper.QuestionMapper;
import com.example.backend.api.resources.exam.module.question.model.Question;
import com.example.backend.api.resources.exam.module.question.filler.QuestionGenerator;
import com.example.backend.api.resources.exam.module.question.service.type.QuestionTypeService;
import com.example.backend.api.resources.exam.module.type.Type;
import com.example.backend.api.resources.exam.module.type.factory.TypeFactoryProvider;
import com.example.backend.api.resources.exam.module.type.factory.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@Service
public class QuestionService {

    private static final Logger LOG = LoggerFactory.getLogger(QuestionService.class);

    private final List<TypeFactory> typeAbstractFactories;

    public QuestionService(TypeFactoryProvider typeFactoryProvider) {
        this.typeAbstractFactories = typeFactoryProvider.getTypeAbstractFactories();
    }


    public List<Question> createQuestions(final int numberOfQuestions) {
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
            QuestionGenerator questionGenerator = typeAbstractFactories.get(randomNum).createFiller();

            try {
                Question question = questionGenerator.generateQuestion(generatedQuestions);
                generatedQuestions.add(question);
            } catch (Exception exception) {
                LOG.error(exception.getMessage());
            }

        }

        return generatedQuestions;
    }

    public List<Question> mapQuestionDTOToQuestion(List<QuestionDTO> questionDTOList) {
        return questionDTOList
                .stream()
                .map(questionDTO -> {
                    Type questionType = questionDTO
                            .getTypeOptional(questionDTO.getType())
                            .orElseThrow(() -> new QuestionDTOBadRequestException("Field type in QuestionDTO is mandatory"));
                    final int questionTypeOrdinal = questionType.ordinal();

                    QuestionMapper questionMapper = typeAbstractFactories.get(questionTypeOrdinal).createQuestionMapper();
                    return questionMapper.mapQuestion(questionDTO);
                })
                .toList();
    }

    public void saveQuestion(Question question){
        final Type questionType = question.getType();
        final int questionTypeOrdinal = questionType.ordinal();
        QuestionTypeService questionTypeService = typeAbstractFactories.get(questionTypeOrdinal).getQuestionTypeService();
        questionTypeService.saveQuestion(question);
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
