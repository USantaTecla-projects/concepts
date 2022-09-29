package com.example.backend.api.resources.exam;

import com.example.backend.api.resources.exam.dto.CreateExamDTO;
import com.example.backend.api.resources.exam.dto.QuestionAndAnswerDTO;
import com.example.backend.api.resources.exam.module.answer.AnswerService;
import com.example.backend.api.resources.exam.module.answer.dto.AnswerDTO;
import com.example.backend.api.resources.exam.module.answer.model.Answer;
import com.example.backend.api.resources.exam.module.question.dto.QuestionDTO;
import com.example.backend.api.resources.exam.dto.ReplyExamDTO;
import com.example.backend.api.resources.exam.exception.specific.CreateExamDTOBadRequestException;
import com.example.backend.api.resources.exam.model.Exam;
import com.example.backend.api.resources.exam.module.question.QuestionService;
import com.example.backend.api.resources.exam.module.question.exception.specific.QuestionDTOBadRequestException;
import com.example.backend.api.resources.exam.module.question.model.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {

    private final QuestionService questionService;

    private final AnswerService answerService;

    public ExamService(
            QuestionService questionService,
            AnswerService answerService
    ) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    public Exam create(CreateExamDTO createExamDTO) {

        final int numberOfQuestions = createExamDTO
                .getNumberOfQuestionsOptional(createExamDTO.getNumberOfQuestions())
                .orElseThrow(() -> new CreateExamDTOBadRequestException("Field numberOfQuestions in CreateExam DTO is mandatory"));

        final List<Question> questions = questionService.createQuestions(numberOfQuestions);

        return new Exam(questions);
    }


    public void reply(final ReplyExamDTO replyExamDTO){

        List<QuestionAndAnswerDTO> questionAndAnswerDTOList = replyExamDTO
                .getQuestionAndAnswerDTOListOptional(replyExamDTO.getQuestionDTOList())
                .orElseThrow(() -> new QuestionDTOBadRequestException("Field questionDTOList in ReplyExam DTO is mandatory"));

        List<QuestionDTO> questionDTOList = questionAndAnswerDTOList
                .stream()
                .map(QuestionAndAnswerDTO::getQuestionDTO)
                .toList();

        List<AnswerDTO> answerDTOList = questionAndAnswerDTOList
                .stream()
                .map(QuestionAndAnswerDTO::getAnswerDTO)
                .toList();

        final List<Question> questions = questionService.mapQuestionDTOToQuestion(questionDTOList);
        final List<Answer> answers = answerService.saveAndGetAnswers(answerDTOList);

        System.out.println(questions);
        System.out.println(answers);
        // Map each answer to its corresponding type

        // Store the exam in the database
    }
}
