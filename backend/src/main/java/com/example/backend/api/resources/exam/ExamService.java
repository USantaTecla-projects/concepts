package com.example.backend.api.resources.exam;

import com.example.backend.api.resources.exam.dto.CreateExamDTO;
import com.example.backend.api.resources.exam.dto.QuestionDTO;
import com.example.backend.api.resources.exam.dto.ReplyExamDTO;
import com.example.backend.api.resources.exam.exception.model.CreateExamDTOBadRequestException;
import com.example.backend.api.resources.exam.model.Exam;
import com.example.backend.api.resources.exam.resources.question.QuestionService;
import com.example.backend.api.resources.exam.resources.question.exception.model.QuestionDTOBadRequestException;
import com.example.backend.api.resources.exam.resources.question.mapper.QuestionMapper;
import com.example.backend.api.resources.exam.resources.question.mapper.QuestionMapperProvider;
import com.example.backend.api.resources.exam.resources.question.model.Question;
import com.example.backend.api.resources.exam.resources.type.Type;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {

    private final QuestionService questionService;

    public ExamService(QuestionService questionService) {
        this.questionService = questionService;
    }

    public Exam create(CreateExamDTO createExamDTO) {

        final int numberOfQuestions = createExamDTO
                .getNumberOfQuestionsOptional(createExamDTO.getNumberOfQuestions())
                .orElseThrow(() -> new CreateExamDTOBadRequestException("Field numberOfQuestions in CreateExam DTO is mandatory"));

        final List<Question> questions = questionService.createQuestions(numberOfQuestions);

        return new Exam(questions);
    }


    public void reply(final ReplyExamDTO replyExamDTO){

        List<QuestionDTO> questionDTOList = replyExamDTO
                .getQuestionDTOListOptional(replyExamDTO.getQuestionDTOList())
                .orElseThrow(() -> new QuestionDTOBadRequestException("Field questionDTOList in ReplyExam DTO is mandatory"));


        for (QuestionDTO questionDTO : questionDTOList){
            Type questionType = questionDTO
                    .getTypeOptional(questionDTO.getType())
                    .orElseThrow(() -> new QuestionDTOBadRequestException("Field type in QuestionDTO is mandatory"));

            QuestionMapper questionMapper = QuestionMapperProvider.valueOf(questionType.name()).getQuestionMapper();
            Question question = questionMapper.mapQuestion(questionDTO);

            // Save in DB?
            System.out.println(question);
        }

        // Map each answer to its corresponding type

        // Store the exam in the database
    }
}
